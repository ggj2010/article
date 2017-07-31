package com.ggj.article.module.common.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ggj.article.module.business.bean.Article;
import com.ggj.article.module.business.bean.Media;

import lombok.extern.slf4j.Slf4j;


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
                    try {
                        Media media = new Media();
                        r.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                        media.setName(r.getCell(0).getStringCellValue());
                        media.setCollectionType(r.getCell(1) == null ? "" : r.getCell(1).getStringCellValue());
                        media.setExampleUrl(r.getCell(2) == null ? "" : r.getCell(2).getStringCellValue());
                        media.setMediaType(r.getCell(3) == null ? "" : r.getCell(3).getStringCellValue());
                        media.setMediaRegion(r.getCell(4) == null ? "" : r.getCell(4).getStringCellValue());
                        media.setPublishSpeed(r.getCell(6) == null ? "" : r.getCell(6).getStringCellValue());
                        media.setBaiduSeo(Integer.parseInt(getIntCellValue(r.getCell(7))));
                        media.setCostPrice(Long.parseLong(getIntCellValue(r.getCell(8))));
                        media.setGoldPrice(Long.parseLong(getIntCellValue(r.getCell(9))));
                        media.setSilverPrice(Long.parseLong(getIntCellValue(r.getCell(10))));
                        media.setBronzePrice(Long.parseLong(getIntCellValue(r.getCell(11))));
                        media.setRemark(r.getCell(14) == null ? "" : r.getCell(14).getStringCellValue());
                        list.add(media);
                    } catch (Exception e) {
                        log.error("解析异常：" + e.getLocalizedMessage());
                    }
                }
                i++;
            }

        } catch (Exception e) {
            log.error("解析上传次文件异常", e);
        } finally {
            wb0.close();
        }
        return list;
    }
    public static List<Article> importArticle(InputStream fileInputStream,Integer userId) throws Exception {
        Workbook wb0 = null;
        List<Article> list = new ArrayList<Article>();
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
                    try {
                        Article article = new Article();
                        r.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        r.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                        list.add(article);
                        article.setMediaName(r.getCell(0).getStringCellValue());
                        article.setTitle(r.getCell(1).getStringCellValue());
                        article.setVerifyUrl(r.getCell(2).getStringCellValue());
                        article.setCustomPrice(Long.parseLong(getIntCellValue(r.getCell(3))));
                        article.setCreateDate(DateUtils.parseDate(r.getCell(4).getStringCellValue()));
                        article.setVerifyDate(DateUtils.parseDate(r.getCell(5).getStringCellValue()));
                        article.setStatus(2);
                        article.setCustomId(userId);
                    } catch (Exception e) {
                        log.error("解析异常：" + e.getLocalizedMessage());
                    }
                }
                i++;
            }

        } catch (Exception e) {
            log.error("解析上传次文件异常", e);
        } finally {
            wb0.close();
        }
        return list;
    }

    private static String getIntCellValue(Cell cell) {
        if (cell == null)
            return "0";
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String value = cell.getStringCellValue();
        if (StringUtils.isEmpty(cell.getStringCellValue())) {
            return "0";
        } else {
            return value;
        }
    }

    public static void exportExel(List<Media> listMedia, HttpServletResponse rep) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("媒体列表");
        BufferedOutputStream bos = null;
        try {
            Long type = UserUtils.getPrincipal().getUserType();
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
            if (type == 0) {
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
                cell = row.createCell((short) 10);
                cell.setCellValue("案例网址");
                cell.setCellStyle(style);
            } else {
                cell = row.createCell((short) 1);
                cell.setCellValue("价格");
                cell.setCellStyle(style);
                cell = row.createCell((short) 2);
                cell.setCellValue("收录类型");
                cell.setCellStyle(style);
                cell = row.createCell((short) 3);
                cell.setCellValue("媒体类型");
                cell.setCellStyle(style);
                cell = row.createCell((short) 4);
                cell.setCellValue("区域");
                cell.setCellStyle(style);
                cell = row.createCell((short) 5);
                cell.setCellValue("发布速度");
                cell.setCellStyle(style);
                cell = row.createCell((short) 6);
                cell.setCellValue("百度权重");
                cell.setCellStyle(style);
                cell = row.createCell((short) 7);
                cell.setCellValue("备注");
                cell.setCellStyle(style);
                cell = row.createCell((short) 8);
                cell.setCellValue("案例网址");
                cell.setCellStyle(style);
            }
            int i = 1;
            for (Media media : listMedia) {
                row = sheet.createRow((int) i);
                row.createCell((short) 0).setCellValue(media.getName());
                if (type == 0) {
                    row.createCell((short) 1).setCellValue(media.getGoldPrice());
                    row.createCell((short) 2).setCellValue(media.getSilverPrice());
                    row.createCell((short) 3).setCellValue(media.getBronzePrice());
                    row.createCell((short) 4).setCellValue(media.getCollectionType());
                    row.createCell((short) 5).setCellValue(media.getMediaType());
                    row.createCell((short) 6).setCellValue(media.getMediaRegion());
                    row.createCell((short) 7).setCellValue(media.getPublishSpeed());
                    row.createCell((short) 8).setCellValue(media.getBaiduSeo() == null ? 1 : media.getBaiduSeo());
                    row.createCell((short) 9).setCellValue(media.getRemark());
                    row.createCell((short) 10).setCellValue(media.getExampleUrl());
                } else {
                    Long price = getCustomPrice(media);
                    row.createCell((short) 1).setCellValue(price);
                    row.createCell((short) 2).setCellValue(media.getCollectionType());
                    row.createCell((short) 3).setCellValue(media.getMediaType());
                    row.createCell((short) 4).setCellValue(media.getMediaRegion());
                    row.createCell((short) 5).setCellValue(media.getPublishSpeed());
                    row.createCell((short) 6).setCellValue(media.getBaiduSeo() == null ? 1 : media.getBaiduSeo());
                    row.createCell((short) 7).setCellValue(media.getRemark());
                    row.createCell((short) 8).setCellValue(media.getExampleUrl());
                }
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
        } catch (Exception e) {
            log.error("导出媒体列表失败", e);
        } finally {
            try {
                if (bos != null) {
                    // bos.close();
                }
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Long getCustomPrice(Media media) {
        String level = UserUtils.getPrincipal().getLevel();
        if ("金牌".equals(level)) {
            return media.getGoldPrice();
        } else if ("银牌".equals(level)) {
            return media.getSilverPrice();
        } else if ("铜牌".equals(level)) {
            return media.getBronzePrice();
        }
        return 0l;
    }

    public static void exportArticleExel(List<Article> listArticle, HttpServletResponse rep, String typeParam) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("媒体列表");
        BufferedOutputStream bos = null;
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
            cell.setCellValue("媒体名称");
            cell = row.createCell((short) 1);
            cell.setCellValue("标题");
            cell.setCellStyle(style);
            cell = row.createCell((short) 2);
            cell.setCellValue("链接");
            cell.setCellStyle(style);
            cell = row.createCell((short) 3);
            cell.setCellValue("价格");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4);
            cell.setCellValue("发布日期");
            cell.setCellStyle(style);
            cell = row.createCell((short) 5);
            cell.setCellValue("审核日期");
            cell.setCellStyle(style);
            cell = row.createCell((short) 6);
            cell.setCellValue("状态");
            cell.setCellStyle(style);
            cell = row.createCell((short) 7);
            cell.setCellValue("结算状态");
            cell.setCellStyle(style);
            cell = row.createCell((short) 8);
            cell.setCellValue("备注");
            cell.setCellStyle(style);
            int i = 1;
            for (Article article : listArticle) {
                row = sheet.createRow((int) i);
                row.createCell((short) 0).setCellValue(article.getMediaName());
                row.createCell((short) 1).setCellValue(article.getTitle());
                //链接
                //如果是已审核就是发布后的链接
                String url = article.getUrl();
                Integer status = article.getStatus();
                String statusName = "";
                //已审核
                if (status == 0) {
                    statusName = "待审核";
                } else if (status == 1) {
                    statusName = "审核中";
                } else if (status == 2) {
                    url = article.getVerifyUrl();
                    statusName = "已审核";
                } else if (status == 3) {
                    statusName = "已退稿";
                } else if (status == 4) {
                    statusName = "已删除";
                }
                row.createCell((short) 2).setCellValue(url);
                Long price = article.getCustomPrice();
                if (typeParam.equals("2")) {
                    price = article.getCostPrice();
                }
                row.createCell((short) 3).setCellValue(price);
                row.createCell((short) 4).setCellValue(DateUtils.formatDateTime(article.getCreateDate()));
                row.createCell((short) 5).setCellValue(article.getVerifyDate() == null ? "" : DateUtils.formatDateTime(article.getVerifyDate()));
                row.createCell((short) 6).setCellValue(statusName);
                row.createCell((short) 7).setCellValue(article.getVerifyStatus() == null ? "未结算" : "已结算");
                row.createCell((short) 8).setCellValue(article.getRemark());
                i++;
            }
            String fileName = "文章列表.xls";
            //处理中文文件名
            rep.setCharacterEncoding("utf-8");
            // 若想下载时自动填好文件名，则需要设置响应头的"Content-disposition"
            rep.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            rep.setCharacterEncoding("utf-8");
            bos = new BufferedOutputStream(rep.getOutputStream());
            rep.flushBuffer();
            wb.write(bos);
            bos.flush();
        } catch (Exception e) {
            log.error("导出文章列表失败", e);
        } finally {
            try {
                if (bos != null) {
                    // bos.close();
                }
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
