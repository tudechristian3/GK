package com.goodkredit.myapplication.model;

import com.goodkredit.myapplication.bean.GKService;

import java.util.ArrayList;
import java.util.List;

public class SectionOrRow {

    private List<GKService> row;
    private ArrayList<String> section;
    private boolean isRow;

    public SectionOrRow createRow(List<GKService> row) {
        SectionOrRow ret = new SectionOrRow();
        ret.row = row;
        ret.isRow = true;
        return ret;
    }

    public  SectionOrRow createSection(ArrayList<String> section) {
        SectionOrRow ret = new SectionOrRow();
        ret.section = section;
        ret.isRow = false;
        return ret;
    }

    public List<GKService> getRow() {
        return row;
    }

    public ArrayList<String> getSection() {
        return section;
    }

    public boolean isRow() {
        return isRow;
    }
}