package backend.service;

//import model.*;

import java.util.Map;

public interface ScenarioService {

    /** 根据算法名字（算法组件的简写）和对应的输入参数调用算法
     *
     * @param algorithmName
     * @param input
     * @return
     */
     Map callAlgorithm(String algorithmName, Map<String,Object> input);



//
//    //Section>Component
//    Section createSection();
//
//    Component createComponent();





}
