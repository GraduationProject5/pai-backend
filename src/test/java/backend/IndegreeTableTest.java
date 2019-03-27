package backend;

import backend.model.bo.IndegreeTable;
import backend.model.po.EdgePO;
import backend.model.po.NodePO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IndegreeTableTest {

    @Test
    public void test1() throws Exception{
        NodePO A = new NodePO();
        A.setNodeID(1L);
        NodePO B = new NodePO();
        B.setNodeID(2L);
        NodePO C = new NodePO();
        C.setNodeID(3L);
        NodePO D = new NodePO();
        D.setNodeID(4L);
        NodePO E = new NodePO();
        E.setNodeID(5L);


        EdgePO AB = new EdgePO();
        AB.setEdgeID(1L);
        AB.setSourceID(1L); AB.setTargetID(2L);
        EdgePO AD = new EdgePO();
        AD.setEdgeID(2L);
        AD.setSourceID(1L); AD.setTargetID(4L);
        EdgePO BC = new EdgePO();
        BC.setEdgeID(3L);
        BC.setSourceID(2L); BC.setTargetID(3L);
        EdgePO CD = new EdgePO();
        CD.setEdgeID(4L);
        CD.setSourceID(3L); CD.setTargetID(4L);
        EdgePO CE = new EdgePO();
        CE.setEdgeID(5L);
        CE.setSourceID(3L); CE.setTargetID(5L);
        EdgePO DE = new EdgePO();
        DE.setEdgeID(6L);
        DE.setSourceID(4L); DE.setTargetID(5L);

        List<NodePO> nodePOList = new ArrayList<>();
        nodePOList.add(A);
        nodePOList.add(B);
        nodePOList.add(C);
        nodePOList.add(D);
        nodePOList.add(E);

        List<EdgePO> edgePOList = new ArrayList<>();
        edgePOList.add(AB);
        edgePOList.add(AD);
        edgePOList.add(BC);
        edgePOList.add(CD);
        edgePOList.add(CE);
        edgePOList.add(DE);

        IndegreeTable it = new IndegreeTable(nodePOList,edgePOList);
        it.getTopologyString();
    }

    @Test
    public void test2() throws Exception{
        NodePO n1 = new NodePO();
        n1.setNodeID(1L);
        NodePO n2 = new NodePO();
        n2.setNodeID(2L);
        NodePO n3 = new NodePO();
        n3.setNodeID(3L);
        NodePO n4 = new NodePO();
        n4.setNodeID(4L);
        NodePO n5 = new NodePO();
        n5.setNodeID(5L);
        NodePO n6 = new NodePO();
        n6.setNodeID(6L);
        NodePO n7 = new NodePO();
        n7.setNodeID(7L);
        NodePO n8 = new NodePO();
        n8.setNodeID(8L);
        NodePO n9 = new NodePO();
        n9.setNodeID(9L);
        NodePO n10 = new NodePO();
        n10.setNodeID(10L);
        NodePO n11 = new NodePO();
        n11.setNodeID(11L);
        NodePO n12 = new NodePO();
        n12.setNodeID(12L);
        NodePO n13 = new NodePO();
        n13.setNodeID(13L);
        NodePO n14 = new NodePO();
        n14.setNodeID(14L);



        EdgePO e1 = new EdgePO();
        e1.setEdgeID(1L);
        e1.setSourceID(1L); e1.setTargetID(2L);
        EdgePO e2 = new EdgePO();
        e2.setEdgeID(2L);
        e2.setSourceID(1L); e2.setTargetID(6L);
        EdgePO e3 = new EdgePO();
        e3.setEdgeID(3L);
        e3.setSourceID(2L); e3.setTargetID(3L);
        EdgePO e4 = new EdgePO();
        e4.setEdgeID(4L);
        e4.setSourceID(6L); e4.setTargetID(7L);
        EdgePO e5 = new EdgePO();
        e5.setEdgeID(5L);
        e5.setSourceID(3L); e5.setTargetID(4L);
        EdgePO e6 = new EdgePO();
        e6.setEdgeID(6L);
        e6.setSourceID(4L); e6.setTargetID(5L);
        EdgePO e7 = new EdgePO();
        e7.setEdgeID(7L);
        e7.setSourceID(4L); e7.setTargetID(8L);
        EdgePO e8 = new EdgePO();
        e8.setEdgeID(8L);
        e8.setSourceID(7L); e8.setTargetID(8L);
        EdgePO e9 = new EdgePO();
        e9.setEdgeID(9L);
        e9.setSourceID(7L); e9.setTargetID(9L);
        EdgePO e10 = new EdgePO();
        e10.setEdgeID(10L);
        e10.setSourceID(5L); e10.setTargetID(10L);
        EdgePO e11 = new EdgePO();
        e11.setEdgeID(11L);
        e11.setSourceID(9L); e11.setTargetID(10L);
        EdgePO e12 = new EdgePO();
        e12.setEdgeID(12L);
        e12.setSourceID(10L); e12.setTargetID(12L);
        EdgePO e13 = new EdgePO();
        e13.setEdgeID(13L);
        e13.setSourceID(10L); e13.setTargetID(11L);
        EdgePO e14 = new EdgePO();
        e14.setEdgeID(14L);
        e14.setSourceID(11L); e14.setTargetID(12L);
        EdgePO e15 = new EdgePO();
        e15.setEdgeID(15L);
        e15.setSourceID(12L); e15.setTargetID(13L);
        EdgePO e16 = new EdgePO();
        e16.setEdgeID(16L);
        e16.setSourceID(13L); e16.setTargetID(14L);

        List<NodePO> nodePOList = new ArrayList<>();
        nodePOList.add(n1);
        nodePOList.add(n2);
        nodePOList.add(n3);
        nodePOList.add(n4);
        nodePOList.add(n5);
        nodePOList.add(n6);
        nodePOList.add(n7);
        nodePOList.add(n8);
        nodePOList.add(n9);
        nodePOList.add(n10);
        nodePOList.add(n11);
        nodePOList.add(n12);
        nodePOList.add(n13);
        nodePOList.add(n14);

        List<EdgePO> edgePOList = new ArrayList<>();
        edgePOList.add(e1);
        edgePOList.add(e2);
        edgePOList.add(e3);
        edgePOList.add(e4);
        edgePOList.add(e5);
        edgePOList.add(e6);
        edgePOList.add(e7);
        edgePOList.add(e8);
        edgePOList.add(e9);
        edgePOList.add(e10);
        edgePOList.add(e11);
        edgePOList.add(e12);
        edgePOList.add(e13);
        edgePOList.add(e14);
        edgePOList.add(e15);
        edgePOList.add(e16);

        IndegreeTable it = new IndegreeTable(nodePOList,edgePOList);
        it.getTopologyString();

        //output is "1,2,6,3,7,4,9,5,8,10,11,12,13,14,"
    }

    @Test
    public void test3(){
//        NodePO n1 = new NodePO();
//        n1.setNodeID(1L);
        NodePO n2 = new NodePO();
        n2.setNodeID(2L);
        NodePO n3 = new NodePO();
        n3.setNodeID(3L);
        NodePO n4 = new NodePO();
        n4.setNodeID(4L);
        NodePO n5 = new NodePO();
        n5.setNodeID(5L);
        NodePO n6 = new NodePO();
        n6.setNodeID(6L);
        NodePO n7 = new NodePO();
        n7.setNodeID(7L);
        NodePO n8 = new NodePO();
        n8.setNodeID(8L);
        NodePO n9 = new NodePO();
        n9.setNodeID(9L);
        NodePO n10 = new NodePO();
        n10.setNodeID(10L);
        NodePO n11 = new NodePO();
        n11.setNodeID(11L);
        NodePO n12 = new NodePO();
        n12.setNodeID(12L);
        NodePO n13 = new NodePO();
        n13.setNodeID(13L);
//        NodePO n14 = new NodePO();
//        n14.setNodeID(14L);



//        EdgePO e1 = new EdgePO();
//        e1.setEdgeID(1L);
//        e1.setSourceID(1L); e1.setTargetID(2L);
//        EdgePO e2 = new EdgePO();
//        e2.setEdgeID(2L);
//        e2.setSourceID(1L); e2.setTargetID(6L);
        EdgePO e3 = new EdgePO();
        e3.setEdgeID(3L);
        e3.setSourceID(2L); e3.setTargetID(3L);
        EdgePO e4 = new EdgePO();
        e4.setEdgeID(4L);
        e4.setSourceID(6L); e4.setTargetID(7L);
        EdgePO e5 = new EdgePO();
        e5.setEdgeID(5L);
        e5.setSourceID(3L); e5.setTargetID(4L);
        EdgePO e6 = new EdgePO();
        e6.setEdgeID(6L);
        e6.setSourceID(4L); e6.setTargetID(5L);
        EdgePO e7 = new EdgePO();
        e7.setEdgeID(7L);
        e7.setSourceID(4L); e7.setTargetID(8L);
        EdgePO e8 = new EdgePO();
        e8.setEdgeID(8L);
        e8.setSourceID(7L); e8.setTargetID(8L);
        EdgePO e9 = new EdgePO();
        e9.setEdgeID(9L);
        e9.setSourceID(7L); e9.setTargetID(9L);
//        EdgePO e10 = new EdgePO();
//        e10.setEdgeID(10L);
//        e10.setSourceID(5L); e10.setTargetID(10L);
        EdgePO e11 = new EdgePO();
        e11.setEdgeID(11L);
        e11.setSourceID(9L); e11.setTargetID(10L);
        EdgePO e12 = new EdgePO();
        e12.setEdgeID(12L);
        e12.setSourceID(10L); e12.setTargetID(12L);
        EdgePO e13 = new EdgePO();
        e13.setEdgeID(13L);
        e13.setSourceID(10L); e13.setTargetID(11L);
        EdgePO e14 = new EdgePO();
        e14.setEdgeID(14L);
        e14.setSourceID(11L); e14.setTargetID(12L);
        EdgePO e15 = new EdgePO();
        e15.setEdgeID(15L);
        e15.setSourceID(12L); e15.setTargetID(13L);
//        EdgePO e16 = new EdgePO();
//        e16.setEdgeID(16L);
//        e16.setSourceID(13L); e16.setTargetID(14L);

        List<NodePO> nodePOList = new ArrayList<>();
//        nodePOList.add(n1);
        nodePOList.add(n2);
        nodePOList.add(n3);
        nodePOList.add(n4);
        nodePOList.add(n5);
        nodePOList.add(n6);
        nodePOList.add(n7);
        nodePOList.add(n8);
        nodePOList.add(n9);
        nodePOList.add(n10);
        nodePOList.add(n11);
        nodePOList.add(n12);
        nodePOList.add(n13);
//        nodePOList.add(n14);

        List<EdgePO> edgePOList = new ArrayList<>();
//        edgePOList.add(e1);
//        edgePOList.add(e2);
        edgePOList.add(e3);
        edgePOList.add(e4);
        edgePOList.add(e5);
        edgePOList.add(e6);
        edgePOList.add(e7);
        edgePOList.add(e8);
        edgePOList.add(e9);
//        edgePOList.add(e10);
        edgePOList.add(e11);
        edgePOList.add(e12);
        edgePOList.add(e13);
        edgePOList.add(e14);
        edgePOList.add(e15);
//        edgePOList.add(e16);

        IndegreeTable it = new IndegreeTable(nodePOList,edgePOList);
        it.getTopologyString();
    }
}
