package com.Lhan.personal_blog.pojo;

public class Book {
    private Long id;

    private String name;

    private String description;

    private String doubanLink;

    private String purchaseLink;

    private String pdfLink;

    private String pictureUrl;

    private String progress;

    private String category;

    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDoubanLink() {
        return doubanLink;
    }

    public void setDoubanLink(String doubanLink) {
        this.doubanLink = doubanLink == null ? null : doubanLink.trim();
    }

    public String getPurchaseLink() {
        return purchaseLink;
    }

    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink == null ? null : purchaseLink.trim();
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink == null ? null : pdfLink.trim();
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", doubanLink='" + doubanLink + '\'' +
                ", purchaseLink='" + purchaseLink + '\'' +
                ", pdfLink='" + pdfLink + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", progress='" + progress + '\'' +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}