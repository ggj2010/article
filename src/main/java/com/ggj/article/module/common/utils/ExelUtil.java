package com.ggj.article.module.common.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ggj.article.module.business.bean.Media;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * @author:gaoguangjin
 * @date 2017/6/25 12:52
 */
@Slf4j
public class ExelUtil {

    public static List<Media> importMediExel(InputStream fileInputStream) throws Exception {
        Workbook wb0 = null;
        List<Media> list = new ArrayList<Media>();
        try {
            wb0 = new HSSFWorkbook(fileInputStream);
            // 获取Excel文档中的第一个表单
            Sheet sht0 = wb0.getSheetAt(0);
            // 对Sheet中的每一行进行迭代
            int i = 0;
            for (Row r : sht0) {
                // 去除第一行
                if (i > 0) {
                    // 去除空行
                    if (r.getRowNum() < 1) {
                        continue;
                    }
                    Media media = new Media();
                    media.setName(r.getCell(0).getStringCellValue());
                    media.setCollectionType(r.getCell(1) == null ? "" : r.getCell(1).getStringCellValue());
                    media.setExampleUrl(r.getCell(2) == null ? "" : r.getCell(2).getStringCellValue());
                    media.setMediaType(r.getCell(3) == null ? "" : r.getCell(3).getStringCellValue());
                    media.setMediaRegion(r.getCell(4) == null ? "" : r.getCell(4).getStringCellValue());
                    media.setPublishSpeed(r.getCell(6) == null ? "" : r.getCell(6).getStringCellValue());
                    r.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    r.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    r.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    r.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    r.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    media.setBaiduSeo(Integer.parseInt(r.getCell(7) == null ? "0" : r.getCell(7).getStringCellValue()));
                    media.setCostPrice(Long.parseLong(r.getCell(8) == null ? "0" : r.getCell(8).getStringCellValue()));
                    media.setGoldPrice(Long.parseLong(r.getCell(9) == null ? "0" : r.getCell(9).getStringCellValue()));
                    media.setSilverPrice(Long.parseLong(r.getCell(10) == null ? "0" : r.getCell(10).getStringCellValue()));
                    media.setBronzePrice(Long.parseLong(r.getCell(11) == null ? "0" : r.getCell(11).getStringCellValue()));
                    media.setRemark(r.getCell(14) == null ? "" : r.getCell(14).getStringCellValue());
                    list.add(media);
                }
                i++;
            }

        } catch (Exception e) {
            log.error("解析上次文件异常", e);
        } finally {
            wb0.close();
        }
        return list;
    }

    public static void exportExel(List<Media> listMedia, HttpServletResponse rep) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("媒体列表");
        BufferedOutputStream bos=null;
        try {
            HSSFRow row = sheet.createRow((int) 0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 14);
            font.setFontName("黑体");
            style.setFont(font);
            HSSFCell cell = row.createCell((short) 0);
            cell.setCellValue("名称");
            cell = row.createCell((short) 1);
            cell.setCellValue("金牌价");
            cell.setCellStyle(style);
            cell = row.createCell((short) 2);
            cell.setCellValue("银牌价");
            cell.setCellStyle(style);
            cell = row.createCell((short) 3);
            cell.setCellValue("铜牌价");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4);
            cell.setCellValue("收录类型");
            cell.setCellStyle(style);
            cell = row.createCell((short) 5);
            cell.setCellValue("媒体类型");
            cell.setCellStyle(style);
            cell = row.createCell((short) 6);
            cell.setCellValue("区域");
            cell.setCellStyle(style);
            cell = row.createCell((short) 7);
            cell.setCellValue("发布速度");
            cell.setCellStyle(style);
            cell = row.createCell((short) 8);
            cell.setCellValue("百度权重");
            cell.setCellStyle(style);
            cell = row.createCell((short) 9);
            cell.setCellValue("备注");
            cell.setCellStyle(style);
            cell = row.createCell((short)10);
            cell.setCellValue("案例网址");
            cell.setCellStyle(style);
            int i=1;
            for (Media media : listMedia) {
                row = sheet.createRow((int) i);
                row.createCell((short) 0).setCellValue(media.getName());
                row.createCell((short) 1).setCellValue(media.getGoldPrice());
                row.createCell((short) 2).setCellValue(media.getSilverPrice());
                row.createCell((short)3).setCellValue(media.getBronzePrice());
                row.createCell((short)4).setCellValue(media.getCollectionType());
                row.createCell((short)5).setCellValue(media.getMediaType());
                row.createCell((short)6).setCellValue(media.getMediaRegion());
                row.createCell((short)7).setCellValue(media.getPublishSpeed());
                row.createCell((short)8).setCellValue(media.getBaiduSeo()==null?1:media.getBaiduSeo());
                row.createCell((short)9).setCellValue(media.getRemark());
                row.createCell((short)10).setCellValue(media.getExampleUrl());
                i++;
            }
            String fileName = "媒体列表.xls";
            //处理中文文件名
            rep.setCharacterEncoding("utf-8");
            // 若想下载时自动填好文件名，则需要设置响应头的"Content-disposition"
            rep.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            rep.setCharacterEncoding("utf-8");
            bos = new BufferedOutputStream(rep.getOutputStream());
            rep.flushBuffer();
            wb.write(bos);
            bos.flush();
        }catch (Exception e){
            log.error("导出媒体列表失败",e);
        }finally {
            try {
                if(bos!=null){
                   // bos.close();
                }
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
