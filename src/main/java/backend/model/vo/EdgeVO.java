package backend.model.vo;

public class EdgeVO {

    public String edgeNo; //输入键为"ID"，使用阿里云组件的编号,如"296b59b7".在数据库存为"edge_no"
    public int index ; //渲染顺序编号
    public String sourceNo; //出发节点的"ID"，使用阿里云组件的编号,如"296b59b7"
    public int sourceAnchor; //出发节点的锚点编号
    public String targetNo; //到达节点的"ID"，使用阿里云组件的编号,如"231f303a"
    public int targetAnchor; //到达节点的锚点编号

}
