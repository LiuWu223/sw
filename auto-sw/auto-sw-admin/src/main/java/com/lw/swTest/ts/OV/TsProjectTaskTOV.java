package com.lw.swTest.ts.OV;

import com.lw.swTest.ts.entity.TsProject;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.lw.swTest.ts.entity.TsTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TsProjectTaskTOV {
    private TsTask tsTask;
    private TsProjectTask tsProjectTask;
}
