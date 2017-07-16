package com.ggj.article.module.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ggj.article.module.business.bean.Media;

import lombok.extern.slf4j.Slf4j;

/**
 * @author:gaoguangjin
 * @date 2017/6/25 12:52
 */
@Slf4j
public class ExelUtil {
	
	public static void main(String[] args) throws Exception {
		importMediExel(null);
	}
	
	public static List<Media> importMediExel(InputStream fileInputStream) throws Exception {
		// Workbook wb0 = new HSSFWorkbook(new FileInputStream(new File("c:text.xls")));
		Workbook wb0 = new HSSFWorkbook(fileInputStream);
		List<Media> list = new ArrayList<Media>();
		// 获取Excel文档中的第一个表单
		Sheet sht0 = wb0.getSheetAt(0);
		// 对Sheet中的每一行进行迭代
		int i = 0;
		for(Row r : sht0) {
			// 去除第一行
			if (i > 0) {
				// 去除空行
				if (r.getRowNum() < 1) {
					continue;
				}
				Media media = new Media();
				media.setName(r.getCell(0).getStringCellValue());
				media.setCollectionType(r.getCell(1)==null?"":r.getCell(1).getStringCellValue());
				media.setExampleUrl(r.getCell(2)==null?"":r.getCell(2).getStringCellValue());
				media.setMediaType(r.getCell(3)==null?"":r.getCell(3).getStringCellValue());
				media.setMediaRegion(r.getCell(4)==null?"":r.getCell(4).getStringCellValue());
				media.setPublishSpeed(r.getCell(6)==null?"":r.getCell(6).getStringCellValue());
				r.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
				r.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
				r.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
				r.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
				r.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
				media.setBaiduSeo(Integer.parseInt(r.getCell(7)==null?"0":r.getCell(7).getStringCellValue()));
				media.setCostPrice(Long.parseLong(r.getCell(8)==null?"0":r.getCell(8).getStringCellValue()));
				media.setGoldPrice(Long.parseLong(r.getCell(9)==null?"0":r.getCell(9).getStringCellValue()));
				media.setSilverPrice(Long.parseLong(r.getCell(10)==null?"0":r.getCell(10).getStringCellValue()));
				media.setBronzePrice(Long.parseLong(r.getCell(11)==null?"0":r.getCell(11).getStringCellValue()));
				media.setRemark(r.getCell(14)==null?"":r.getCell(14).getStringCellValue());
				list.add(media);
			}
			i++;
		}
		return list;
	}
}
