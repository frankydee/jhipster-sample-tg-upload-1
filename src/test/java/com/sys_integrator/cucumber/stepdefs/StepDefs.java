package com.sys_integrator.cucumber.stepdefs;

import com.sys_integrator.TgUpload1App;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = TgUpload1App.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
