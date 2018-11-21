package cn.com.izj.amap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 轨迹图片生成时所需的相关参数(不包含坐标点位)
 *
 * @author lifang
 */
@Component
@ConfigurationProperties(prefix = "staticmap")
@PropertySource(value = "classpath:amap-config.properties")
public class StaticMapParams {

    /**
     * 地图缩放级别:[1,17],可选，无默认值
     */
//    private String zoom;

    /**
     * 图片宽度*图片高度。最大值为1024*1024，可选，默认400*400
     */
    private String size;

    /**
     * 线条粗细。可选值： [2,15]，默认5
     */
    private String weight;

    /**
     * 折线颜色。 选值范围：[0x000000, 0xffffff],默认0x0000FF
     * 例如：0x000000 black,0x008000 green,0x800080 purple,0xFFFF00 yellow,0x0000FF blue,0x808080 gray,0xffa500 orange,0xFF0000 red,0xFFFFFF white
     */
    private String color;

    /**
     * 透明度。可选值[0,1]，小数后最多2位，0表示完全透明，1表示完全不透明，默认1。
     */
    private String transparency;

    /**
     * 多边形的填充颜色，此值不为空时折线封闭成多边形。取值规则同color，无默认值
     */
    @Value("")
    private String fillcolor;

    /**
     * 填充面透明度。可选值[0,1]，小数后最多2位，0表示完全透明，1表示完全不透明,默认0.5。
     */
    @Value("")
    private String fillTransparency;


//    public String getZoom() {
//        return zoom;
//    }
//
//    public void setZoom(String zoom) {
//        this.zoom = zoom;
//    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public String getFillcolor() {
        return fillcolor;
    }

    public void setFillcolor(String fillcolor) {
        this.fillcolor = fillcolor;
    }

    public String getFillTransparency() {
        return fillTransparency;
    }

    public void setFillTransparency(String fillTransparency) {
        this.fillTransparency = fillTransparency;
    }
}
