package com.lw.swTest.core.generate;

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
        String moduleName = "campus";
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/micro_question_answer?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.author("xiefan").enableSpringdoc() // 设置作者
                            .outputDir("D:\\IntelliJ IDEA\\21\\micro-question-answer\\micro-question-answer-wechat\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xiefan.education").moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\IntelliJ IDEA\\21\\micro-question-answer\\micro-question-answer-wechat\\src\\main\\java\\com\\xiefan\\education\\" + moduleName + "\\mapper\\xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // system:
                    // "system_dict", "system_dict_detailed", "system_permissions", "system_region", "system_role", "system_role_permissions", "system_user", "system_user_role"
                    // campus:
                    // "campus_class", "campus_grade", "campus_knowledge_points", "campus_people_attribute", "campus_school", "campus_school_set", "campus_school_type", "campus_student", "campus_subject", "campus_subject_classify", "campus_teacher", "campus_teacher_subject"
                    // question:
                    // "question_accumulate_points_record", "question_accumulate_points_rule", "question_answer", "question_ask", "question_ask_browse", "question_ask_collection", "question_ask_comment", "question_ask_like", "question_chase_ask_answer", "question_notice", "question_notice_object", "question_order_record", "question_privacy_issues_object", "question_rush_answer_record", "question_user", "question_wechat_user"
                    // business
                    // "bs_province", "bs_city", "bs_area", "bs_street"
                    builder.addInclude("campus_class", "campus_grade", "campus_knowledge_points", "campus_people_attribute", "campus_school", "campus_school_set", "campus_school_type", "campus_student", "campus_subject", "campus_subject_classify", "campus_teacher", "campus_teacher_subject")
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
