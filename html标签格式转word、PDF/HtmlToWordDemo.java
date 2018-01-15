package com.liujj.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Base64;
import java.util.List;

import org.apache.poi.POIDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwmf.record.HwmfBitmap16;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.rtf.RtfWriter2;


public class HtmlToWordDemo {

	public static void main(String[] args) throws Exception {
		String head = "<html><head></head><body>";
		String content ="<p style='margin-bottom:8px;text-align:center'><strong><span style='font-size:24px;font-family:宋体'>有关互联网开户系统修改的需求</span></strong><strong></strong></p><p style='text-indent:32px;line-height:150%'><strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>一、系统新开户：</span></strong></p><p style='text-indent:32px;line-height:150%'><span style='font-size:16px;line-height:150%;font-family:宋体'>1</span><span style='font-size:16px;line-height:150%;font-family:宋体'>、客户通过手机短信验证，登录互联网开户系统；</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size:16px;line-height:150%;font-family:宋体'>2</span><span style='font-size:16px;line-height:150%;font-family:宋体'>、客户阅读“三协议”<span>(</span>《期货交易风险说明书》、《客户须知》、《期货经纪合同》<span>)</span>、上传身份证彩照、填写基本信息、上传银行卡；</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size:16px;line-height:150%;font-family:宋体'>3</span><span style='font-size:16px;line-height:150%;font-family:宋体'>、客户可选择 “专业投资者”或“普通投资者”：</span></p><p style='text-indent:32px;line-height:150%'><strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>（<span>1</span>） “普通投资者”专属流程：</span></strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>点击下一步操作后，将填写风险承受能力问卷。风险承受能力问卷要求支持两种题目类型，一类为单选题、一类为多选题。计分规则为单选题取客户勾选的选项对应分数计入总分，多选题取客户所选各项的最高分计入总分，风险测评问卷可由期货公司自行导入试题<span>,</span>设置每个答案分数和每类风险级别对应的分数段。</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size: 16px;line-height:150%;font-family:宋体'>目前投资者分为<span>5</span>级，由低到高为<span>C1</span>、<span>C2</span>、<span>C3</span>、<span>C4</span>和<span>C5</span>。默认设置的风险承受能力分数区间为：<span>0</span>（含）<span>-30</span>分为<span>C1</span>，<span>30</span>（含）<span>-50</span>分为<span>C2</span>，<span>50</span>（含）<span>-60</span>分为<span>C3</span>，<span>60</span>（含）<span>-80</span>分为<span>C4</span>，<span>80</span>（含）<span>-100</span>（含）分为<span>C5</span>。该分值区间期货公司在上传时可调整。</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size: 16px;line-height:150%;font-family:宋体'>用户点击提交风险承受能力问卷后，网页页面根据作答情况实时反馈客户所属的风险承受能力类别。（举例：“<strong>尊敬的客户您好，您所填写的风险承受能力问卷得分为<span>XX</span>分，您属于‘<span>XX</span>型风险承受能力投资者’，<span>C1</span>型、<span>C2</span>型投资者仅可购买相关资管产品；<span>C3</span>型投资者可开立商品期货账户；<span> C4</span>型、<span>C5</span>型投资者可开立商品期货、商品期权、金融期货、原油期货账户。请知晓。</strong>”）客户可在风险承受能力结果提示页面中点击“重新进行风险承受能力评估”按钮，重做进行风险承受能力试题（历次结果可作为流水，显示在“用户开户查询”菜单中的客户信息流水中，但客户信息的页面上只显示最新的分数）。</span></p><p style='text-indent:32px;line-height:150%'><span style='font-size: 16px;line-height:150%;font-family:宋体'>若风险承受能力问卷支持判别是否为最低类别投资者（若问卷中某些题选了特定答案，则客户类别自动归为<span>C1</span>，属最低类别投资者，可在系统里设置相关题目及选项的标识），若是，则终止开户流程。</span></p><p style='text-indent:32px;line-height:150%'><strong><span style='font-size:16px;line-height:150%;font-family: 宋体'>（<span>2</span>）“专业投资者”专属流程：</span></strong></p><p><span style='font-size:16px;font-family:宋体'>客户选择“专业投资者”且进入“专业投资者”专属流程后，增加了解专业投资者信息页面，该页面显示“追加了解以下信息，请您如实填写”。</span></p><p><br/></p>";
		InputStream ins = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\muxuzi"));
		byte[] bytes = new byte[ins.available()];
        // 将文件中的内容读入到数组中
        ins.read(bytes);
        String base64s = Base64.getEncoder().encodeToString(bytes);    //将字节流数组转换为字符串
        base64s = "data:image/jpeg;base64,"+base64s.replace("\n", "\r\n").replace("\r\r\n", "\r\n");;
//        base64s = "C:\\Users\\Administrator\\Desktop\\muxuzi";
//		String img = "<img width='100px' height='100px' src='"+base64s+"'>";
        String img = "";
		content = head + content + img + "<div></div>${sign1}</body></html>";
//		String path = poiWord(content);
		downloadWord("");
		
//		itextWord(content);
//		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\word.html"));
//		ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
//		int b;  
//        while((b=in.read())!= -1)  
//        {  
//        	out.write(b);  
//        }
//        in.close();
//        out.close();
//        poiWord(content);
	}
	
	/**
	 * itext导出word
	 */
	public static void itextWord(String content){
		OutputStream out = null;
		Document document = null;
		try {
			out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\bb.doc"));
			document = new Document(PageSize.A4);  
	        RtfWriter2 writer2 = RtfWriter2.getInstance(document, out);  
	        document.open();  
	        Paragraph context = new Paragraph(); 
	        StyleSheet ss = new StyleSheet();  
	        List htmlList = HTMLWorker.parseToList(new StringReader(content), ss);  
	        for (int i = 0; i < htmlList.size(); i++)
	        {  
	            com.lowagie.text.Element e = (com.lowagie.text.Element) htmlList.get(i);  
	            context.add(e);  
	        }  
	        document.add(context);  
	        System.out.println("ok");  
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(document!=null){
					document.close();  
				}
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * poi导出word
	 * @param content
	 */
	public static String poiWord(String content){
		ByteArrayInputStream in = null;
//		FileInputStream in = null;
		FileOutputStream out = null;
		String path="C:\\Users\\Administrator\\Desktop\\aa.doc";
		try {
			in = new ByteArrayInputStream(content.getBytes());
//			in = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\word.html"));
			POIFSFileSystem poi = new POIFSFileSystem();
			DirectoryNode root = poi.getRoot();
			DocumentEntry document = root.createDocument("WordDocument", in);
			out = new FileOutputStream(new File(path));
			poi.writeFilesystem(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
			if(in!=null)
					in.close();
			if(out!=null)
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	public static void downloadWord(String path) throws IOException, DocumentException{
//		FileInputStream ins = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\cc.doc"));
		FileInputStream in = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\bb.doc"));
//		Document document = new Document();
		FileOutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\a.doc",true);
		//out.write("test".getBytes());
//		ByteArrayOutputStream outs = new ByteArrayOutputStream();
//		RtfWriter2 instance = RtfWriter2.getInstance(document, out);
//		instance.importRtfDocument(in);
//		document.open();
		 //定义缓冲字节数组
        byte[] array = new byte[102400];
        //读取长度
        int len = 0;
        while((len = in.read(array)) > 0){
            out.write(array,0,len);
        }
//        while((len = ins.read(array)) > 0){
//            outs.write(array,0,len);
//        }
        
		
		
//		outs.write("/r/n".getBytes());
		
		
//	    Image img = Image.getInstance("C:\\Users\\Administrator\\Desktop\\muxuzi");
//        img.setAbsolutePosition(0, 100);                               
//        img.setAlignment(Image.ALIGN_CENTER);// 设置图片显示位置                  
//        img.scalePercent(30);//表示显示的大小为原尺寸的30%
//        img.setBorder(2);
//        img.setTop(0);
//        document.add(img);
//        
//	    document.close();
//	    out.write(outs.toByteArray());
//		out.write(outs.toByteArray());
//		outs.flush();
//	    outs.close();
	    out.close();
	}
	
	

}
