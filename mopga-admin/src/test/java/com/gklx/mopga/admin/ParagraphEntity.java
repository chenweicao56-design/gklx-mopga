package com.gklx.mopga.admin;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ParagraphEntity extends TrunkEntity {

    private String textIndent;

    private String styleName;

    private List<TrunkEntity> trunks;

    public List<TrunkEntity> getTrunks() {
        return trunks;
    }

    public void setTrunks(List<TrunkEntity> trunks) {
        if (CollectionUtil.isNotEmpty(trunks)) {
            Map<String, List<TrunkEntity>> group = trunks.stream().collect(Collectors.groupingBy(TrunkEntity::toString));
            if (group.size() == 1) {
                this.setColor(trunks.get(0).getColor());
                this.setFontFamily(trunks.get(0).getFontFamily());
                this.setFontSize(trunks.get(0).getFontSize());
                this.setTextAlign(trunks.get(0).getTextAlign());
                this.setTextWeight(trunks.get(0).getTextWeight());
            } else {
                this.trunks = trunks;
                this.setText("");
            }

        } else {
            this.trunks = trunks;
        }
    }



    public String getTextIndent() {
        return textIndent;
    }

    public void setTextIndent(String textIndent) {
        this.textIndent = textIndent;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
}
