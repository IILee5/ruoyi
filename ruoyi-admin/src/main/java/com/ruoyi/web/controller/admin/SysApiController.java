package com.ruoyi.web.controller.admin;

import java.util.List;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.model.domain.SysApi;
import com.ruoyi.service.ISysApiService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * API密钥Controller
 *
 * @author iilee
 * @date 2022-09-06
 */
@Controller
@RequestMapping("/admin/api")
public class SysApiController extends BaseController
{
    private String prefix = "admin/api";

    @Autowired
    private ISysApiService sysApiService;

    @RequiresPermissions("admin:api:view")
    @GetMapping()
    public String api()
    {
        return prefix + "/api";
    }

    /**
     * 查询API密钥列表
     */
    @RequiresPermissions("admin:api:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysApi sysApi)
    {
        startPage();
        List<SysApi> list = sysApiService.selectSysApiList(sysApi);
        return getDataTable(list);
    }

    /**
     * 导出API密钥列表
     */
    @RequiresPermissions("admin:api:export")
    @Log(title = "API密钥", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysApi sysApi)
    {
        List<SysApi> list = sysApiService.selectSysApiList(sysApi);
        ExcelUtil<SysApi> util = new ExcelUtil<>(SysApi.class);
        return util.exportExcel(list, "API密钥数据");
    }

    /**
     * 新增API密钥
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存API密钥
     */
    @RepeatSubmit
    @RequiresPermissions("admin:api:add")
    @Log(title = "API密钥", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysApi sysApi)
    {
        return toAjax(sysApiService.insertSysApi(sysApi));
    }

    /**
     * 修改API密钥
     */
    @RequiresPermissions("admin:api:edit")
    @GetMapping("/edit/{apiId}")
    public String edit(@PathVariable("apiId") Integer apiId, ModelMap mmap)
    {
        SysApi sysApi = sysApiService.selectSysApiByApiId(apiId);
        mmap.put("sysApi", sysApi);
        return prefix + "/edit";
    }

    /**
     * 修改保存API密钥
     */
    @RequiresPermissions("admin:api:edit")
    @Log(title = "API密钥", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysApi sysApi)
    {
        return toAjax(sysApiService.updateSysApi(sysApi));
    }

    /**
     * 删除API密钥
     */
    @RequiresPermissions("admin:api:remove")
    @Log(title = "API密钥", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysApiService.deleteSysApiByApiIds(ids));
    }
}
