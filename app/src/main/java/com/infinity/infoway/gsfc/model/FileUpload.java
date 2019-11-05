package com.infinity.infoway.gsfc.model;

import java.util.List;

public class FileUpload {
    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * lt_is_doc_attachment : 1
         * lt_is_doc_compulsory : 1
         */

        private String lt_is_doc_attachment;
        private String lt_is_doc_compulsory;

        public String getLt_is_doc_attachment() {
            return lt_is_doc_attachment;
        }

        public void setLt_is_doc_attachment(String lt_is_doc_attachment) {
            this.lt_is_doc_attachment = lt_is_doc_attachment;
        }

        public String getLt_is_doc_compulsory() {
            return lt_is_doc_compulsory;
        }

        public void setLt_is_doc_compulsory(String lt_is_doc_compulsory) {
            this.lt_is_doc_compulsory = lt_is_doc_compulsory;
        }
    }
}
