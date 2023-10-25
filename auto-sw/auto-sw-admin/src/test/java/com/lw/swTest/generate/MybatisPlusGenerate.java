package com.lw.swTest.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

public class MybatisPlusGenerate {

    /**
     * 主入口函数
     * @param args
     */
    public static void main(String[] args) {
        String moduleName = "ts";
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/seewo_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.enableSwagger().author("lw") // 设置作者
                            //
                            .outputDir("C:/Users/user/Desktop/自动化平台测试项目/sw/auto-sw/auto-sw-admin/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.lw.swTest").moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:/Users/user/Desktop/自动化平台测试项目/sw/auto-sw/auto-sw-admin/src/main/java/com/lw/swTest/" + moduleName + "/mapper/xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // system:
                    // "system_user_role","system_user","system_role_permissions","system_role","system_permissions"
                    // swtest:
                    // "swtest_case_module_item", "swtest_case_module", "swtest_case_item_detail", "swtest_case_device"
                    // "sy_authority","sy_dict","sy_role","sy_role_authority","sy_role_route","sy_route","sy_setting","sy_user","sy_user_role"
                    // "ts_case","ts_case_itme","ts_case_result","ts_case_step","ts_module","ts_task","ts_task_details"
                    // "tol_adb","tol_git"
                    builder.addInclude("ts_trilateral_case")
                            .controllerBuilder().enableRestStyle()
                            .entityBuilder().idType(IdType.ASSIGN_ID).logicDeleteColumnName("delete_flag")
                            .addTableFills(new Column("create_time", FieldFill.INSERT),
                                    new Column("create_by", FieldFill.INSERT),
                                    new Column("delete_flag", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE),
                                    new Column("update_by", FieldFill.INSERT_UPDATE)).enableLombok(); // 设置需要生成的表名
                    // .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


}
