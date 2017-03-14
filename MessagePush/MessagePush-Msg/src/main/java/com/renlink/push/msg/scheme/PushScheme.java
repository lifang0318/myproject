package com.renlink.push.msg.scheme;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.renlink.push.msg.lang.PlatformType;

public abstract class PushScheme extends BaseScheme {

	private static final long serialVersionUID = 1L;

	private PlatformType platform = PlatformType.ALL;

	private Set<String> tags;
	private Set<String> alias;
	private String title;
	private Map<String, String> extras;

	public void addTag(String tag) {
		if (tags == null) {
			tags = new HashSet<String>();
		}
		tags.add(tag);
	}

	public void addAlia(String alia) {
		if (alias == null) {
			alias = new HashSet<String>();
		}
		alias.add(alia);
	}

	public PlatformType getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformType platform) {
		this.platform = platform;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, String> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}

	public void addExtra(String key, String value) {
		if (extras == null) {
			extras = new HashMap<String, String>();
		}
		extras.put(key, value);
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Set<String> getAlias() {
		return alias;
	}

	public void setAlias(Set<String> alias) {
		this.alias = alias;
	}

}
