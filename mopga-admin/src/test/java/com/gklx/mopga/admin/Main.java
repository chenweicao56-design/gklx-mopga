package com.gklx.mopga.admin;

import cn.hutool.core.collection.ListUtil;
import com.spire.doc.Document;
import com.spire.doc.DocumentObject;
import com.spire.doc.Section;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;
import com.spire.doc.formatting.CharacterFormat;
import com.spire.doc.formatting.ParagraphFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Document document = new Document();
        document.loadFromFile("C:\\Users\\gklx\\Desktop\\627生产总结 （布洛德峰）V2.docx");
        List<ParagraphEntity> ParagraphEntities = new ArrayList<>();
        for (int i = 0; i < document.getSections().getCount(); i++) {
            Section section = document.getSections().get(i);
            for (int j = 0; j < section.getParagraphs().getCount(); j++) {
                Paragraph paragraph = section.getParagraphs().get(j);
                String paragraphText = paragraph.getText();
                ParagraphEntity paragraphEntity = new ParagraphEntity();
                paragraphEntity.setStyleName(paragraph.getStyleName());
                ParagraphEntities.add(paragraphEntity);
                paragraphEntity.setText(paragraphText);
                ParagraphFormat paragraphFormat = paragraph.getFormat();
                HorizontalAlignment horizontalAlignment = paragraphFormat.getHorizontalAlignment();
                paragraphEntity.setTextAlign(horizontalAlignment.name());
                float firstLineIndent = paragraphFormat.getFirstLineIndent();
                paragraphEntity.setTextIndent(firstLineIndent+"磅");
                List<TrunkEntity> trunkEntities = new ArrayList<>();
                for (int k = 0; k < paragraph.getChildObjects().getCount(); k++) {
                    System.out.println("段索引：" + k);
                    DocumentObject obj = paragraph.getChildObjects().get(k);
                    if (obj instanceof TextRange) {
                        TrunkEntity trunkEntity = new TrunkEntity();
                        trunkEntities.add(trunkEntity);
                        TextRange textRange = (TextRange) obj;
                        String text = textRange.getText();
                        System.out.println("段内容：" + text);
                        if (!text.trim().isEmpty()) {
                            CharacterFormat format = textRange.getCharacterFormat();
                            // 字体名称
                            String fontName = format.getFontName();
                            trunkEntity.setFontFamily(fontName);
                            float fontSize = format.getFontSize();
                            trunkEntity.setFontSize(fontSize + "磅");
                            // 字体颜色
                            Color textColor = format.getTextColor();
                            int red = textColor.getRed();
                            int green = textColor.getGreen();
                            int blue = textColor.getBlue();
                            // 2. 转换为十六进制颜色码（如 #FF0000 表示红色）
                            String hexColor = String.format("#%02X%02X%02X", red, green, blue);
                            trunkEntity.setColor(hexColor);
                            // 粗体/斜体
                            boolean isBold = format.getBold();
                            boolean isItalic = format.getItalic();
                            trunkEntity.setTextWeight(isBold ? "bold" : "normal");
                        }
                    }
                }
                paragraphEntity.setTrunks(trunkEntities);

            }
        }

        System.out.println(ParagraphEntities);
    }

}
