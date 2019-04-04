package backend.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ExperimentInfoVO {

    private long experimentID;

    private List<NodeVO> nodes;

    private List<EdgeVO> edges;

}
