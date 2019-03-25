package backend.service.impl;

import backend.daorepository.TextsRepository;
import backend.service.TextAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文本分析组件
 */
@Service
public class TextAnalysisServiceImpl implements TextAnalysisService {
    @Autowired
    TextsRepository textsRepository;
    @Override
    public void getParticiples() {
//        textsRepository.getOne();
    }

}
