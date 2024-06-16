package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.dto.MajorDto;
import com.dgu_csc.akomanager_back.model.Major;

public interface MajorService {
    public void saveMajor(Major major);

    public Integer getTotalScore(MajorDto majorDto);
    public Integer getTotalMajorScore(MajorDto majorDto);

    public Integer getTotalCommonScore(MajorDto majorDto);

    Integer getTotalDesignatedScore(MajorDto majorDto);
}
