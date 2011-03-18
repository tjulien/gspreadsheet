package org.gsimple.spreadsheet;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CSV {
    private Iterable<String> csv;
    public CSV(String email, String password, String spreadsheetTitle, String worksheetTitle) throws ServiceException, IOException {
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
                        csv = new CSVIterator(service.getFeed(listFeedUrl, ListFeed.class));
                    }
                }
            }
        }
    }

    public Iterable<String> getRows() {
        return csv;
    }

    private static class CSVIterator implements Iterator<String>, Iterable<String> {
        private final Iterator<ListEntry> entries;

        public CSVIterator(ListFeed feed) {
            this.entries = feed.getEntries().iterator();
        }

        public Iterator<String> iterator() {
            return this;
        }

        public boolean hasNext() {
            return entries.hasNext();
        }

        public String next() {
            ListEntry listEntry = entries.next();
            StringBuffer line = new StringBuffer();
            for (String tag : listEntry.getCustomElements().getTags()) {
                line.append("\"").append(listEntry.getCustomElements().getValue(tag)).append("\"");
                line.append(",");
            }
            if(line.length() > 0) {
                line.deleteCharAt(line.length() - 1);
            }
            return line.toString();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
