package org.gsimple.spreadsheet;


import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Spreadsheet {
    private List<Map<String, String>> rowView = new ArrayList<Map<String, String>>();
    private Map<String, List<String>> columnView = new HashMap<String, List<String>>();

    public Spreadsheet(String email, String password, String spreadsheetTitle, String worksheetTitle) throws ServiceException, IOException {
        SpreadsheetService service = new SpreadsheetService("gsimple-gspreadsheet-1");
        service.setUserCredentials(email, password);
        URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
        SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
        for (SpreadsheetEntry entry : spreadsheets) {
            if(entry.getTitle().getPlainText().equals(spreadsheetTitle)) {
                List<WorksheetEntry> worksheets = entry.getWorksheets();
                for (WorksheetEntry worksheet : worksheets) {
                    if(worksheet.getTitle().getPlainText().equals(worksheetTitle)){
                        URL listFeedUrl = worksheet.getListFeedUrl();
                        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
                        for (ListEntry listEntry : listFeed.getEntries()) {
                            Map<String, String> row = new TreeMap<String, String>();
                            rowView.add(row);
                            for (String tag : listEntry.getCustomElements().getTags()) {
                                if(!columnView.containsKey(tag)) {
                                    columnView.put(tag, new ArrayList<String>());
                                }
                                row.put(tag, listEntry.getCustomElements().getValue(tag));
                                columnView.get(tag).add(listEntry.getCustomElements().getValue(tag));
                            }
                        }
                    }
                }
            }
        }
    }

    public List<Map<String, String>> getRowView() {
        return rowView;
    }

    public Map<String, List<String>> getColumnView() {
        return columnView;
    }
}
