package com.orion.foody;

public class NewReleaseModel {
    private String id;
    private String version;
    private String url;
    private String changelog;
    private String release_date;

    public NewReleaseModel(String id, String version, String url, String changelog, String release_date) {
        this.id = id;
        this.version = version;
        this.url = url;
        this.changelog = changelog;
        this.release_date = release_date;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public String getChangelog() {
        return changelog;
    }

    public String getRelease_date() {
        return release_date;
    }
}
