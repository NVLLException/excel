package com.excel.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.excel.entity.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mqjia on 5/31/2018.
 *
 */
public class ExcelUtil {
    public static Map<String,Object> parseExcel2Form(String excelFileName) throws Exception {
        Map<String,Object> result = new HashMap();
        List<String> fieldNames = new ArrayList();
        String fieldNamePrefix = "fieldName_";
        int fieldCount = 1;

        HSSFSheet sheet = null;
        StringBuffer lsb = new StringBuffer();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    excelFileName)); // 获整个Excel
            //构造form
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                sheet = workbook.getSheetAt(sheetIndex);// 获所有的sheet
                String sheetName = workbook.getSheetName(sheetIndex); // sheetName
                if (workbook.getSheetAt(sheetIndex) != null) {
                    sheet = workbook.getSheetAt(sheetIndex);// 获得不为空的这个sheet
                    if (sheet != null) {
                        int firstRowNum = sheet.getFirstRowNum(); // 第一行
                        int lastRowNum = sheet.getLastRowNum(); // 最后一行
                        // 构造Table
                        lsb.append("<center><table name='fileTable' width=\"70%\" style=\"border:1px solid #000;border-width:1px 0 0 1px;margin:2px 0 2px 0;border-collapse:collapse;\">");
                        for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                            if (sheet.getRow(rowNum) != null) {// 如果行不为空，
                                HSSFRow row = sheet.getRow(rowNum);
                                short firstCellNum = row.getFirstCellNum(); // 该行的第一个单元格
                                short lastCellNum = row.getLastCellNum(); // 该行的最后一个单元格
                                int height = (int) (row.getHeight() / 15.625); // 行的高度
                                lsb.append("<tr height=\""
                                        + height
                                        + "\" style=\"border:1px solid #000;border-width:0 1px 1px 0;margin:2px 0 2px 0;\">");
                                for (short cellNum = firstCellNum; cellNum <= lastCellNum; cellNum++) { // 循环该行的每一个单元格
                                    HSSFCell cell = row.getCell(cellNum);
                                    if (cell != null) {
                                        if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                                            continue;
                                        } else {
                                            StringBuffer tdStyle = new StringBuffer(
                                                    "<td style=\"border:1px solid #000; border-width:0 1px 1px 0;margin:2px 0 2px 0; ");
                                            HSSFCellStyle cellStyle = cell
                                                    .getCellStyle();
                                            HSSFPalette palette = workbook
                                                    .getCustomPalette(); // 类HSSFPalette用于求颜色的国际标准形式
                                            HSSFColor hColor = palette
                                                    .getColor(cellStyle
                                                            .getFillForegroundColor());
                                            HSSFColor hColor2 = palette
                                                    .getColor(cellStyle
                                                            .getFont(workbook)
                                                            .getColor());

                                            String bgColor = convertToStardColor(hColor);// 背景颜色
                                            short boldWeight = cellStyle
                                                    .getFont(workbook)
                                                    .getBoldweight(); // 字体粗细
                                            short fontHeight = (short) (cellStyle
                                                    .getFont(workbook)
                                                    .getFontHeight() / 2); // 字体大小
                                            String fontColor = convertToStardColor(hColor2); // 字体颜色
                                            if (bgColor != null
                                                    && !"".equals(bgColor
                                                    .trim())) {
                                                tdStyle.append(" background-color:"
                                                        + bgColor + "; ");
                                            }
                                            if (fontColor != null
                                                    && !"".equals(fontColor
                                                    .trim())) {
                                                tdStyle.append(" color:"
                                                        + fontColor + "; ");
                                            }
                                            tdStyle.append(" font-weight:"
                                                    + boldWeight + "; ");
                                            tdStyle.append(" font-size: "
                                                    + fontHeight + "%;");
                                            lsb.append(tdStyle + "\"");

                                            int width = (int) (sheet
                                                    .getColumnWidth(cellNum) / 35.7); //
                                            int cellReginCol = getMergerCellRegionCol(
                                                    sheet, rowNum, cellNum); // 合并的列（solspan）
                                            int cellReginRow = getMergerCellRegionRow(
                                                    sheet, rowNum, cellNum);// 合并的行（rowspan）
                                            String align = convertAlignToHtml(cellStyle
                                                    .getAlignment()); //
                                            String vAlign = convertVerticalAlignToHtml(cellStyle
                                                    .getVerticalAlignment());

                                            lsb.append(" align=\"" + align
                                                    + "\" valign=\"" + vAlign
                                                    + "\" width=\"" + width
                                                    + "\" ");
                                            lsb.append(" colspan=\""
                                                    + cellReginCol
                                                    + "\" rowspan=\""
                                                    + cellReginRow + "\"");
                                            Object value = getCellValue(cell);

                                            Object finalValue = "";
                                            if("\"\"".equals(value)){
                                                String fieldName = fieldNamePrefix + fieldCount++;
                                                fieldNames.add(fieldName);
                                                finalValue = "<textarea name='" + fieldName + "' style='width:100%;height:100%;resize:none;background:#FFFFF0' placeholder=''></textarea>";
                                            } else if("N/A".equals(value)){
                                            } else {
                                                finalValue = value;
                                            }
                                            lsb.append(">" + finalValue + "</td>");
                                        }
                                    }
                                }
                                lsb.append("</tr>");
                            }
                        }
                        lsb.append("</table></center>");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("文件 " + excelFileName + " 没有找到!");
        } catch (IOException e) {
            throw new Exception("文件 " + excelFileName + " 处理错误("
                    + e.getMessage() + ")!");
        }
        result.put("html",lsb);
        result.put("fieldList", fieldNames);
        return result;
    }

    private static Object getCellValue(HSSFCell cell) throws IOException {
        Object value = "";
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            value = cell.getRichStringCellValue().toString();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                value = sdf.format(date);
            } else {
                double value_temp = (double) cell.getNumericCellValue();
                BigDecimal bd = new BigDecimal(value_temp);
                BigDecimal bd1 = bd.setScale(3, bd.ROUND_HALF_UP);
                value = bd1.doubleValue();

                /*
                 * DecimalFormat format = new DecimalFormat("#0.###"); value =
                 * format.format(cell.getNumericCellValue());
                 */
            }
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
            value = "";
        }
        return value;
    }

    private static int getMergerCellRegionCol(HSSFSheet sheet, int cellRow,
                                              int cellCol) throws IOException {
        int retVal = 0;
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cra = (CellRangeAddress) sheet.getMergedRegion(i);
            int firstRow = cra.getFirstRow(); // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
            if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
                if (cellCol >= firstCol && cellCol <= lastCol) {
                    retVal = lastCol - firstCol + 1; // 得到合并的列数
                    break;
                }
            }
        }
        return retVal;
    }

    private static int getMergerCellRegionRow(HSSFSheet sheet, int cellRow,
                                              int cellCol) throws IOException {
        int retVal = 0;
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cra = (CellRangeAddress) sheet.getMergedRegion(i);
            int firstRow = cra.getFirstRow(); // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
            if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
                if (cellCol >= firstCol && cellCol <= lastCol) {
                    retVal = lastRow - firstRow + 1; // 得到合并的行数
                    break;
                }
            }
        }
        return retVal;
    }

    private static String convertToStardColor(HSSFColor hc) {
        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            int a = HSSFColor.AUTOMATIC.index;
            int b = hc.getIndex();
            if (a == b) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                String str;
                String str_tmp = Integer.toHexString(hc.getTriplet()[i]);
                if (str_tmp != null && str_tmp.length() < 2) {
                    str = "0" + str_tmp;
                } else {
                    str = str_tmp;
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }

    private static String convertAlignToHtml(short alignment) {
        String align = "left";
        switch (alignment) {
            case HSSFCellStyle.ALIGN_LEFT:
                align = "left";
                break;
            case HSSFCellStyle.ALIGN_CENTER:
                align = "center";
                break;
            case HSSFCellStyle.ALIGN_RIGHT:
                align = "right";
                break;
            default:
                break;
        }
        return align;
    }

    private static String convertVerticalAlignToHtml(short verticalAlignment) {
        String valign = "middle";
        switch (verticalAlignment) {
            case HSSFCellStyle.VERTICAL_BOTTOM:
                valign = "bottom";
                break;
            case HSSFCellStyle.VERTICAL_CENTER:
                valign = "center";
                break;
            case HSSFCellStyle.VERTICAL_TOP:
                valign = "top";
                break;
            default:
                break;
        }
        return valign;
    }


    public static String generateTableSql(String tableName, List<String> fileds) throws Exception{
        if(fileds != null && fileds.size() > 0 && !StringUtils.isEmpty(tableName)){
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table ").append(tableName).append("(");
            buffer.append("id int(11) auto_increment,");
            for(String filed : fileds){
                buffer.append(filed).append(" varchar(1000),");
            }
            buffer.append("userId int(11),");
            buffer.append("createBy int(11),");
            buffer.append("createTime datetime,");

            buffer.append("primary key(`id`)");
            buffer.append(")ENGINE=INNODB,CHARSET=utf8;");
            return buffer.toString();
        }
        return "";
    }

    public static String generateFieldDataSql(String tableName, List<String> fields, List<String> values, User user){
        StringBuffer buffer = new StringBuffer();
        buffer.append("insert into ").append(tableName).append("(");
        for(int i=0; i<fields.size();i++){
            buffer.append("`").append(fields.get(i)).append("`").append(",");
        }
        buffer.append("userId,createBy,createTime");
        buffer.append(")values(");
        for(int i=0; i<values.size();i++){
            buffer.append("'").append(values.get(i)).append("'").append(",");
        }
        buffer.append(user.getId()).append(",").append(user.getId()).append(",").append("now()");
        buffer.append(")");
        return buffer.toString();
    }

    public static String editFileDataSql(String tableName, List<String> fields, List<String> values, User user, String id){
        StringBuffer buffer = new StringBuffer();
        buffer.append("update " + tableName + " set ");
        for(int i=0; i< fields.size(); i++){
            buffer.append(fields.get(i) + " = " + ( !StringUtils.isEmpty(values.get(i)) ? "'" + values.get(i) + "'" : "null"));
            buffer.append(",");
        }
        buffer.append(" userId=" + user.getId());
        buffer.append(" where id=" + id);
        return buffer.toString();
    }

    public static void removeSession(HttpServletRequest request) {
        Enumeration<String> sessionNams = request.getSession().getAttributeNames();
        while (sessionNams.hasMoreElements()){
            request.getSession().removeAttribute(sessionNams.nextElement());
        }
    }
}
