package com.michaelkunynets.excelparcer;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadXls {
    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(ReadXls.class.getName());
    }

    //83 length
    private static final String[] className = {"102", "103", "104", "105", "106", "109", "14", "15", "16", "17", "18А",
            "18база", "203", "204", "205", "206", "207", "208", "210А", "210В", "210С", "212", "219", "220", "224",
            "225", "226", "227", "228", "232", "235", "301", "302", "303", "304", "305", "306", "312", "319", "32",
            "321А", "321В", "322", "331", "332", "333", "334", "35 НПРЧ", "421", "428", "429", "431", "432", "439",
            "440", "441", "7", "БазаГДЗС", "Л 101", "Л 101А", "Л 111", "Л 114", "Л 221", "Л 234", "Л 31", "Л 320",
            "Л 324", "Л 401", "Л 407", "Л 411", "Л 412", "Л 413", "Л 414", "Л 422", "НПРЧ", "С зал1", "С зал2",
            "С зал3", "С зал4", "Спзал1", "Спзал2", "Спзал3", "Спзал4"};

    public static String[] getClassName() {
        return className;
    }

    private String filePath;

    public ReadXls(String filePath) {
        this.filePath = filePath;
        LOGGER.info("File path get");
    }

    public void Read() {
        try {
            //File path
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            LOGGER.info("File open correctly");
            Iterator rows = sheet.iterator();
            Date dateTmp = null;
            List<CellRangeAddress> regionsList = new ArrayList<CellRangeAddress>();
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                regionsList.add(sheet.getMergedRegion(i));
            }
            while (rows.hasNext()) {
                Row row = (Row) rows.next();
                // Skip header
                if (row.getRowNum() == 0)
                    continue;
                // Open class what will send structured data
                Classroom cr = new Classroom();
                Iterator cells = row.cellIterator();
                // While cells exists
                while (cells.hasNext()) {
                    // Get next cell
                    Cell cell = (Cell) cells.next();
                    switch (cell.getColumnIndex()) {
                        // Get and Set Date
                        case 0:
                            if (cell.getCellType() == CellType.BLANK)
                                cr.setDate(dateTmp);
                            else {
                                dateTmp = cell.getDateCellValue();
                                cr.setDate(dateTmp);
                            }
                            break;
                        // Skip but don't write day and time of lesson
                        case 1:
                        case 3:
                            break;
                        // Get and Set subject number per day
                        case 2:
                            cr.setSubjectNumber(String.valueOf(cell.getNumericCellValue()));
                            break;
                        // Get and Set what will be in classroom in some period of time
                        default:
                            // Get and Set number of classroom
                            cr.setAuditorium(className[cell.getColumnIndex() - 4]);
                            // If read data is empty check is it combined cells
                            if (cell.getCellType() == CellType.BLANK) {
                                cr.setEmpty(true);
                                for (CellRangeAddress region : regionsList) {

                                    // If the region does contain the cell you have just read from the row
                                    if (region.isInRange(cell.getRowIndex(), cell.getColumnIndex())) {
                                        // Now, you need to get the cell from the top left hand corner of this
                                        int rowNum = region.getFirstRow();
                                        int colIndex = region.getFirstColumn();
                                        cell = sheet.getRow(rowNum).getCell(colIndex);
                                        cr.setEmpty(false);
                                        Sprint(cell.getStringCellValue(), cr);
                                        break;
                                    }
                                }
                                break;
                            }
                            Sprint(cell.getStringCellValue(), cr);
                            break;
                    }
                }
            }
            file.close();
            System.out.println("Reading File Done!");
        } catch (Exception e) {
            LOGGER.warning("File open incorrectly");
            LOGGER.warning("Exception :- " + e.getMessage());
        }
    }

    // Get index of element ( not only first and last )
    private static int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    private static void Sprint(String string, Classroom cr) {
        int latin = ordinalIndexOf(string, ".", 1);
        cr.setTeacher(string.substring(0, latin + 1));
        string = string.substring(latin + 2);// Str without teacher
        latin = string.lastIndexOf('(');
        cr.setSubjectType(string.substring(latin));
        string = string.substring(0, latin - 2); // str only with group and subject
        if (string.contains(",")) {
            latin = string.lastIndexOf(",");
            latin = latin + ordinalIndexOf(string.substring(latin), " ", 2);
        } else {
            latin = string.indexOf(" ");
        }
        cr.setGroup(string.substring(0, latin));
        cr.setSubject(string.substring(latin));
        cr.SendData();
    }
}
