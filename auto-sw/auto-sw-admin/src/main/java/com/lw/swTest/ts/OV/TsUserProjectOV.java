package com.lw.swTest.ts.OV;

import com.lw.swTest.sy.entity.SyUser;
import com.lw.swTest.sy.entity.SyUserRole;
import com.lw.swTest.ts.entity.TsProject;
import com.lw.swTest.ts.entity.TsUserProject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TsUserProjectOV implements Serializable {
    private TsProject tsProject;
    private TsUserProject tsUserProject;
}
