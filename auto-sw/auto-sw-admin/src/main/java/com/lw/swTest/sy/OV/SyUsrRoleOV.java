package com.lw.swTest.sy.OV;

import com.lw.swTest.sy.entity.SyUser;
import com.lw.swTest.sy.entity.SyUserRole;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SyUsrRoleOV  implements Serializable {
    private SyUser syUser;
    private SyUserRole syUserRole;
}
