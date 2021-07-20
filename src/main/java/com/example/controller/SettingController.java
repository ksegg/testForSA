package com.example.controller;

import com.example.constant.Constants;
import com.example.service.SettingService;
import com.example.vo.StHeaderVo;
import com.example.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Validated
public class SettingController {

    @Resource
    private SettingService settingService;

    @RequestMapping("/stHeader")
    public String headerList(Model model) {
        //Global Headers
        List<StHeaderVo> stHeaderList = settingService.getStHeaders();
        model.addAttribute("stHeaderList", stHeaderList);
        return "settings/st_header_list";
    }

    @RequestMapping("/stHeader/add")
    public String headerAdd(Model model) {
        model.addAttribute("stHeaderVo", new StHeaderVo());
        return "settings/st_header_add";
    }

    @RequestMapping(value = "/stHeader/create", method = RequestMethod.POST)
    public String headerCreate(@Validated StHeaderVo stHeaderVo, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "settings/st_header_add";
        }
        // stHeaderVo
        UserVo u = (UserVo) session.getAttribute(Constants.SYS_002);
        if (u != null){
            stHeaderVo.setCreatedBy(u.getLoginId());
            stHeaderVo.setUpdatedBy(u.getLoginId());
        }

        settingService.addStHeader(stHeaderVo);
        return "redirect:/stHeader";
    }

    @GetMapping("/stHeader/{id}")
    public String headerShow(@PathVariable Long id, Model model) {
        StHeaderVo stHeaderVo = settingService.findStHeaderById(id);
        model.addAttribute("stHeaderVo", stHeaderVo);
        return "settings/st_header_edit";
    }

    @RequestMapping(value="/stHeader/save", method=RequestMethod.POST)
    public String headerUpdate(@Validated StHeaderVo stHeaderVo, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "settings/st_header_edit";
        }
        // stHeaderVo
        UserVo u = (UserVo) session.getAttribute(Constants.SYS_002);
        if (u != null){
            stHeaderVo.setUpdatedBy(u.getLoginId());
        }
        settingService.updateStHeader(stHeaderVo);
        return "redirect:/stHeader";
    }

    @GetMapping("/stHeader/{id}/delete")
    public String headerDelete(@PathVariable Long id, Model model) {
        settingService.deleteStHeader(id);
        return "redirect:/stHeader";
    }
}
