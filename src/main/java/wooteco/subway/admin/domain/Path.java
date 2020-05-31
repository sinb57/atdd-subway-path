package wooteco.subway.admin.domain;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import wooteco.subway.admin.exception.NoExistPathException;

public class Path {
    private final WeightedMultigraph<Long, PathEdge> graph;

    public Path() {
        graph = new WeightedMultigraph<>(PathEdge.class);
    }

    public void setEdges(List<Line> lines, PathType pathType) {
        for (Line line : lines) {
            List<LineStation> lineStations = line.getSortedLineStations();
            addVertexes(lineStations);
            List<LineStation> edgeStations = filterValidEdgeStations(lineStations);
            addEdgesWithWeight(edgeStations, pathType);
        }
    }

    private void addVertexes(List<LineStation> lineStations) {
        for (LineStation lineStation : lineStations) {
            graph.addVertex(lineStation.getStationId());
        }
    }

    private List<LineStation> filterValidEdgeStations(List<LineStation> lineStations) {
        return lineStations.stream()
            .filter(LineStation::isNotFirstLineStation)
            .collect(Collectors.toList());
    }

    private void addEdgesWithWeight(List<LineStation> lineStations, PathType pathType) {
        for (LineStation lineStation : lineStations) {
            PathEdge pathEdge = PathEdge.of(lineStation, pathType);
            graph.addEdge(lineStation.getPreStationId(), lineStation.getStationId(), pathEdge);
            graph.setEdgeWeight(pathEdge, pathEdge.getWeight());
        }
    }

    public GraphPath<Long, PathEdge> searchShortestPath(Station source, Station target) {
        validateSourceTarget(source, target);

        return calculateShortestPath(source, target);
    }

    private GraphPath<Long, PathEdge> calculateShortestPath(Station source, Station target) {
        try {
            return DijkstraShortestPath.findPathBetween(graph, source.getId(), target.getId());
        } catch (IllegalArgumentException e) {
            throw new NoExistPathException("출발역과 도착역이 연결되어 있지 않습니다.");
        }
    }

    private void validateSourceTarget(Station source, Station target) {
        if (source.equals(target)) {
            throw new InvalidParameterException("출발역과 도착역은 같을 수 없습니다.");
        }
    }

    public int calculateDistance(GraphPath<Long, PathEdge> path) {
        return path.getEdgeList()
            .stream()
            .mapToInt(PathEdge::getDistance)
            .sum();
    }

    public int calculateDuration(GraphPath<Long, PathEdge> path) {
        return path.getEdgeList()
            .stream()
            .mapToInt(PathEdge::getDuration)
            .sum();
    }
}