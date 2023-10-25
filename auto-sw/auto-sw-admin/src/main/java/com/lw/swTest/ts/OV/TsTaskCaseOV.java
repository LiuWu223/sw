package com.lw.swTest.ts.OV;

import com.lw.swTest.ts.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TsTaskCaseOV {
    private TsTask tsTask;
    private TsTaskDetails tsTaskDetails;
    private TsProjectTask tsProjectTask;
    private Long[] caseIds;
}
