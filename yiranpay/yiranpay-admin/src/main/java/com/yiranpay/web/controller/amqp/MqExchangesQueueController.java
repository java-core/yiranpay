package com.yiranpay.web.controller.amqp;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiranpay.amqp.domain.MqExchangesQueue;
import com.yiranpay.amqp.service.IMqExchangesQueueService;
import com.yiranpay.common.annotation.Log;
import com.yiranpay.common.core.controller.BaseController;
import com.yiranpay.common.core.domain.AjaxResult;
import com.yiranpay.common.core.page.TableDataInfo;
import com.yiranpay.common.enums.BusinessType;
import com.yiranpay.common.utils.poi.ExcelUtil;
/**
 * RabbitMQ交换器和队列关联 信息操作处理
 * 
 * @author yiran
 * @date 2019-04-28
 */
@Controller
@RequestMapping("/amqp/mqExchangesQueue")
public class MqExchangesQueueController extends BaseController
{
    private String prefix = "amqp/mqExchangesQueue";
	
	@Autowired
	private IMqExchangesQueueService mqExchangesQueueService;
	
	@RequiresPermissions("amqp:mqExchangesQueue:view")
	@GetMapping()
	public String mqExchangesQueue()
	{
	    return prefix + "/mqExchangesQueue";
	}
	
	/**
	 * 查询RabbitMQ交换器和队列关联列表
	 */
	@RequiresPermissions("amqp:mqExchangesQueue:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MqExchangesQueue mqExchangesQueue)
	{
		startPage();
        List<MqExchangesQueue> list = mqExchangesQueueService.selectMqExchangesQueueList(mqExchangesQueue);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出RabbitMQ交换器和队列关联列表
	 */
	@RequiresPermissions("amqp:mqExchangesQueue:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MqExchangesQueue mqExchangesQueue)
    {
    	List<MqExchangesQueue> list = mqExchangesQueueService.selectMqExchangesQueueList(mqExchangesQueue);
        ExcelUtil<MqExchangesQueue> util = new ExcelUtil<MqExchangesQueue>(MqExchangesQueue.class);
        return util.exportExcel(list, "mqExchangesQueue");
    }
	
	/**
	 * 新增RabbitMQ交换器和队列关联
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存RabbitMQ交换器和队列关联
	 */
	@RequiresPermissions("amqp:mqExchangesQueue:add")
	@Log(title = "RabbitMQ交换器和队列关联", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(MqExchangesQueue mqExchangesQueue)
	{		
		return toAjax(mqExchangesQueueService.insertMqExchangesQueue(mqExchangesQueue));
	}

	/**
	 * 修改RabbitMQ交换器和队列关联
	 */
	@GetMapping("/edit/{exchangesId}")
	public String edit(@PathVariable("exchangesId") Integer exchangesId, ModelMap mmap)
	{
		MqExchangesQueue mqExchangesQueue = mqExchangesQueueService.selectMqExchangesQueueById(exchangesId);
		mmap.put("mqExchangesQueue", mqExchangesQueue);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存RabbitMQ交换器和队列关联
	 */
	@RequiresPermissions("amqp:mqExchangesQueue:edit")
	@Log(title = "RabbitMQ交换器和队列关联", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(MqExchangesQueue mqExchangesQueue)
	{		
		return toAjax(mqExchangesQueueService.updateMqExchangesQueue(mqExchangesQueue));
	}
	
	/**
	 * 删除RabbitMQ交换器和队列关联
	 */
	@RequiresPermissions("amqp:mqExchangesQueue:remove")
	@Log(title = "RabbitMQ交换器和队列关联", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(mqExchangesQueueService.deleteMqExchangesQueueByIds(ids));
	}
	
}
