package com.liujj.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;

public class HtmlToPdfDemo {

	public static void main(String[] args) throws Exception {
		String head = "<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'> <head><meta http-equiv='Content-Type' content='text/html;charset=UTF-8'></meta></head><body>";
		String content ="<p style='margin-bottom:8px;text-align:center'><strong><span style='font-size:24px;font-family:宋体'>有关互联网开户系统修改的需求</span></strong><strong></strong></p><p style='text-indent:32px;line-height:150%'><strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>一、系统新开户：</span></strong></p><p style='text-indent:32px;line-height:150%'><span style='font-size:16px;line-height:150%;font-family:宋体'>1</span><span style='font-size:16px;line-height:150%;font-family:宋体'>、客户通过手机短信验证，登录互联网开户系统；</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size:16px;line-height:150%;font-family:宋体'>2</span><span style='font-size:16px;line-height:150%;font-family:宋体'>、客户阅读“三协议”<span>(</span>《期货交易风险说明书》、《客户须知》、《期货经纪合同》<span>)</span>、上传身份证彩照、填写基本信息、上传银行卡；</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size:16px;line-height:150%;font-family:宋体'>3</span><span style='font-size:16px;line-height:150%;font-family:宋体'>、客户可选择 “专业投资者”或“普通投资者”：</span></p><p style='text-indent:32px;line-height:150%'><strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>（<span>1</span>） “普通投资者”专属流程：</span></strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>点击下一步操作后，将填写风险承受能力问卷。风险承受能力问卷要求支持两种题目类型，一类为单选题、一类为多选题。计分规则为单选题取客户勾选的选项对应分数计入总分，多选题取客户所选各项的最高分计入总分，风险测评问卷可由期货公司自行导入试题<span>,</span>设置每个答案分数和每类风险级别对应的分数段。</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size: 16px;line-height:150%;font-family:宋体'>目前投资者分为<span>5</span>级，由低到高为<span>C1</span>、<span>C2</span>、<span>C3</span>、<span>C4</span>和<span>C5</span>。默认设置的风险承受能力分数区间为：<span>0</span>（含）<span>-30</span>分为<span>C1</span>，<span>30</span>（含）<span>-50</span>分为<span>C2</span>，<span>50</span>（含）<span>-60</span>分为<span>C3</span>，<span>60</span>（含）<span>-80</span>分为<span>C4</span>，<span>80</span>（含）<span>-100</span>（含）分为<span>C5</span>。该分值区间期货公司在上传时可调整。</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size: 16px;line-height:150%;font-family:宋体'>用户点击提交风险承受能力问卷后，网页页面根据作答情况实时反馈客户所属的风险承受能力类别。（举例：“<strong>尊敬的客户您好，您所填写的风险承受能力问卷得分为<span>XX</span>分，您属于‘<span>XX</span>型风险承受能力投资者’，<span>C1</span>型、<span>C2</span>型投资者仅可购买相关资管产品；<span>C3</span>型投资者可开立商品期货账户；<span> C4</span>型、<span>C5</span>型投资者可开立商品期货、商品期权、金融期货、原油期货账户。请知晓。</strong>”）客户可在风险承受能力结果提示页面中点击“重新进行风险承受能力评估”按钮，重做进行风险承受能力试题（历次结果可作为流水，显示在“用户开户查询”菜单中的客户信息流水中，但客户信息的页面上只显示最新的分数）。</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size: 16px;line-height:150%;font-family:宋体'>若风险承受能力问卷支持判别是否为最低类别投资者（若问卷中某些题选了特定答案，则客户类别自动归为<span>C1</span>，属最低类别投资者，可在系统里设置相关题目及选项的标识），若是，则终止开户流程。</span></p><p style='text-indent:32px;line-height:150%'><strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>（<span>2</span>）“专业投资者”专属流程：</span></strong></p><p><span style='font-size:16px;font-family:宋体'>客户选择“专业投资者”且进入“专业投资者”专属流程后，增加了解专业投资者信息页面，该页面显示“追加了解以下信息，请您如实填写”。</span></p><p><br/></p>";
//		InputStream ins = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\muxuzi"));
//		byte[] bytes = new byte[ins.available()];
        // 将文件中的内容读入到数组中
//        ins.read(bytes);
//        String base64s = Base64.getEncoder().encodeToString(bytes);    //将字节流数组转换为字符串
//        base64s = "data:image/jpeg;base64,"+base64s;
//        base64s = "C:\\Users\\Administrator\\Desktop\\muxuzi";
//		String img = "<img width='100px' height='100px' src='"+base64s+"'>";
        String img = "";
		content = head + content + img + "</body></html>";
		htmlToPdf(content);
	}
	
	//html转PDF
	public static void htmlToPdf(String content) throws Exception{
		
//		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\temp.html"));
		ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
//		int b;  
//        while((b=in.read())!= -1)  
//        {  
//        	out.write(b);  
//        }
//        in.close();
//        out.close();
		
		Document document = new Document();
		PdfWriter writer = PdfWriter. getInstance(document, new FileOutputStream("C:\\Users\\Administrator\\Desktop\\temp.pdf"));
		PDFBuilder builder = new PDFBuilder("C:\\Users\\Administrator\\Desktop\\muxuzi", "C:\\Users\\Administrator\\Desktop\\muxuzi");
		writer.setPageEvent(builder);
		// 设置字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font fontChinese = new Font(bfChinese, 10, Font.NORMAL);
        document.open();
        
        float[] widths11 = { 100f };// 设置表格的列宽和列数6
		PdfPTable table11 = new PdfPTable(widths11);// 建立一个pdf表格
		table11.setSpacingBefore(20f);
		table11.setWidthPercentage(100);// 设置表格宽度为100%

		int chNum = 1;
		Chapter chapter = new Chapter(new Paragraph(
				"HTML文件转PDF元素，便于追加其他内容", fontChinese), chNum++);
		Section section = chapter.addSection(new Paragraph("详解",fontChinese));
		
		PdfPCell cell11 = new PdfPCell(new Paragraph("我是一个单元格",fontChinese));
		cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell11.setBackgroundColor(BaseColor.GRAY);
		cell11.setMinimumHeight(20);
		table11.addCell(cell11);
        List<Element> listEle = new ArrayList<Element>();
        ElementHandler handler = new ElementHandler() {
			
			@Override
			public void add(Writable w) {
				if (w instanceof WritableElement) {
					listEle.addAll(((WritableElement) w).elements());
				}
			}
		};
        XMLWorkerHelper. getInstance().parseXHtml(handler, in, Charset.forName("utf-8"));
        List<Element> list = new ArrayList<Element>();
        for (Element element : listEle) {
        	if (element instanceof LineSeparator
					|| element instanceof WritableDirectElement) {
				continue;
			}
			list.add(element);
		}
        section.addAll(list);
        //cell11.addElement(section);
        document.add(section);
        Image signImg = Image.getInstance("C:\\Users\\Administrator\\Desktop\\muxuzi");
		//自定义大小
        signImg.setAbsolutePosition(60,10);
		signImg.scaleAbsolute(200,200);
		document.add(signImg);
		Image OfficialSealImg = Image.getInstance("C:\\Users\\Administrator\\Desktop\\muxuzi");
		//自定义大小
		OfficialSealImg.setAbsolutePosition(330,10);
		OfficialSealImg.scaleAbsolute(200,200);
		document.add(OfficialSealImg);
        document.close();
        in.close();
	}

}
