package com.fongmi.android.tv.bean;

import android.net.Uri;
import android.text.TextUtils;

import com.fongmi.android.tv.App;
import com.fongmi.android.tv.Constant;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Live {

    @SerializedName("type")
    private int type;
    @SerializedName("boot")
    private boolean boot;
    @SerializedName("pass")
    private boolean pass;
    @SerializedName("name")
    private String name;
    @SerializedName("group")
    private String group;
    @SerializedName("url")
    private String url;
    @SerializedName("logo")
    private String logo;
    @SerializedName("epg")
    private String epg;
    @SerializedName("ua")
    private String ua;
    @SerializedName("referer")
    private String referer;
    @SerializedName("timeout")
    private Integer timeout;
    @SerializedName("header")
    private JsonElement header;
    @SerializedName("playerType")
    private Integer playerType;
    @SerializedName("channels")
    private List<Channel> channels;
    @SerializedName("groups")
    private List<Group> groups;
    @SerializedName("core")
    private Core core;

    private boolean activated;
    private int width;

    public static Live objectFrom(JsonElement element) {
        return App.gson().fromJson(element, Live.class);
    }

    public static List<Live> arrayFrom(String str) {
        Type listType = new TypeToken<List<Live>>() {}.getType();
        List<Live> items = App.gson().fromJson(str, listType);
        return items == null ? Collections.emptyList() : items;
    }

    public Live() {
    }

    public Live(String url) {
        this.name = url.startsWith("file") ? new File(url).getName() : Uri.parse(url).getLastPathSegment();
        this.url = url;
    }

    public Live(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public boolean isBoot() {
        return boot;
    }

    public boolean isPass() {
        return pass;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name;
    }

    public String getGroup() {
        return TextUtils.isEmpty(group) ? "" : group;
    }

    public String getUrl() {
        return TextUtils.isEmpty(url) ? "" : url;
    }

    public String getLogo() {
        return TextUtils.isEmpty(logo) ? "" : logo;
    }

    public String getEpg() {
        return TextUtils.isEmpty(epg) ? "" : epg;
    }

    public String getUa() {
        return TextUtils.isEmpty(ua) ? "" : ua;
    }

    public String getReferer() {
        return TextUtils.isEmpty(referer) ? "" : referer;
    }

    public Integer getTimeout() {
        return timeout == null ? Constant.TIMEOUT_PLAY : Math.max(timeout, 1) * 1000;
    }

    public JsonElement getHeader() {
        return header;
    }

    public int getPlayerType() {
        return playerType == null ? -1 : Math.min(playerType, 2);
    }

    public List<Channel> getChannels() {
        return channels = channels == null ? new ArrayList<>() : channels;
    }

    public List<Group> getGroups() {
        return groups = groups == null ? new ArrayList<>() : groups;
    }

    public Core getCore() {
        return core == null ? new Core() : core;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setActivated(Live item) {
        this.activated = item.equals(this);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Live check() {
        boolean proxy = getChannels().size() > 0 && getChannels().get(0).getUrls().size() > 0 && getChannels().get(0).getUrls().get(0).startsWith("proxy");
        if (proxy) setProxy();
        return this;
    }

    private void setProxy() {
        this.url = getChannels().get(0).getUrls().get(0);
        this.name = getChannels().get(0).getName();
        this.type = 2;
    }

    public Group find(Group item) {
        for (Group group : getGroups()) if (group.getName().equals(item.getName())) return group;
        getGroups().add(item);
        return item;
    }

    public boolean hasLogo() {
        for (Group group : getGroups()) if (group.getLogo().length() > 0) return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Live)) return false;
        Live it = (Live) obj;
        return getName().equals(it.getName());
    }
}
