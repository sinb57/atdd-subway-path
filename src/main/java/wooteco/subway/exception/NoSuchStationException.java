package wooteco.subway.exception;

import java.util.NoSuchElementException;

public class NoSuchStationException extends NoSuchElementException {

    public NoSuchStationException(long id) {
        super(String.format("지하철역을 찾을 수 없습니다. [id : %d]", id));
    }
}
