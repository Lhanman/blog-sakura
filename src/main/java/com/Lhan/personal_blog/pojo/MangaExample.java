package com.Lhan.personal_blog.pojo;

import java.util.ArrayList;
import java.util.List;

public class MangaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MangaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(Long value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(Long value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(Long value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(Long value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(Long value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(Long value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<Long> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<Long> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(Long value1, Long value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(Long value1, Long value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andCntitleIsNull() {
            addCriterion("cnTitle is null");
            return (Criteria) this;
        }

        public Criteria andCntitleIsNotNull() {
            addCriterion("cnTitle is not null");
            return (Criteria) this;
        }

        public Criteria andCntitleEqualTo(String value) {
            addCriterion("cnTitle =", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleNotEqualTo(String value) {
            addCriterion("cnTitle <>", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleGreaterThan(String value) {
            addCriterion("cnTitle >", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleGreaterThanOrEqualTo(String value) {
            addCriterion("cnTitle >=", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleLessThan(String value) {
            addCriterion("cnTitle <", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleLessThanOrEqualTo(String value) {
            addCriterion("cnTitle <=", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleLike(String value) {
            addCriterion("cnTitle like", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleNotLike(String value) {
            addCriterion("cnTitle not like", value, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleIn(List<String> values) {
            addCriterion("cnTitle in", values, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleNotIn(List<String> values) {
            addCriterion("cnTitle not in", values, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleBetween(String value1, String value2) {
            addCriterion("cnTitle between", value1, value2, "cntitle");
            return (Criteria) this;
        }

        public Criteria andCntitleNotBetween(String value1, String value2) {
            addCriterion("cnTitle not between", value1, value2, "cntitle");
            return (Criteria) this;
        }

        public Criteria andJptitleIsNull() {
            addCriterion("jpTitle is null");
            return (Criteria) this;
        }

        public Criteria andJptitleIsNotNull() {
            addCriterion("jpTitle is not null");
            return (Criteria) this;
        }

        public Criteria andJptitleEqualTo(String value) {
            addCriterion("jpTitle =", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleNotEqualTo(String value) {
            addCriterion("jpTitle <>", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleGreaterThan(String value) {
            addCriterion("jpTitle >", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleGreaterThanOrEqualTo(String value) {
            addCriterion("jpTitle >=", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleLessThan(String value) {
            addCriterion("jpTitle <", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleLessThanOrEqualTo(String value) {
            addCriterion("jpTitle <=", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleLike(String value) {
            addCriterion("jpTitle like", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleNotLike(String value) {
            addCriterion("jpTitle not like", value, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleIn(List<String> values) {
            addCriterion("jpTitle in", values, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleNotIn(List<String> values) {
            addCriterion("jpTitle not in", values, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleBetween(String value1, String value2) {
            addCriterion("jpTitle between", value1, value2, "jptitle");
            return (Criteria) this;
        }

        public Criteria andJptitleNotBetween(String value1, String value2) {
            addCriterion("jpTitle not between", value1, value2, "jptitle");
            return (Criteria) this;
        }

        public Criteria andEntitleIsNull() {
            addCriterion("enTitle is null");
            return (Criteria) this;
        }

        public Criteria andEntitleIsNotNull() {
            addCriterion("enTitle is not null");
            return (Criteria) this;
        }

        public Criteria andEntitleEqualTo(String value) {
            addCriterion("enTitle =", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleNotEqualTo(String value) {
            addCriterion("enTitle <>", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleGreaterThan(String value) {
            addCriterion("enTitle >", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleGreaterThanOrEqualTo(String value) {
            addCriterion("enTitle >=", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleLessThan(String value) {
            addCriterion("enTitle <", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleLessThanOrEqualTo(String value) {
            addCriterion("enTitle <=", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleLike(String value) {
            addCriterion("enTitle like", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleNotLike(String value) {
            addCriterion("enTitle not like", value, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleIn(List<String> values) {
            addCriterion("enTitle in", values, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleNotIn(List<String> values) {
            addCriterion("enTitle not in", values, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleBetween(String value1, String value2) {
            addCriterion("enTitle between", value1, value2, "entitle");
            return (Criteria) this;
        }

        public Criteria andEntitleNotBetween(String value1, String value2) {
            addCriterion("enTitle not between", value1, value2, "entitle");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNull() {
            addCriterion("startDate is null");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNotNull() {
            addCriterion("startDate is not null");
            return (Criteria) this;
        }

        public Criteria andStartdateEqualTo(String value) {
            addCriterion("startDate =", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotEqualTo(String value) {
            addCriterion("startDate <>", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThan(String value) {
            addCriterion("startDate >", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThanOrEqualTo(String value) {
            addCriterion("startDate >=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThan(String value) {
            addCriterion("startDate <", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThanOrEqualTo(String value) {
            addCriterion("startDate <=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLike(String value) {
            addCriterion("startDate like", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotLike(String value) {
            addCriterion("startDate not like", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateIn(List<String> values) {
            addCriterion("startDate in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotIn(List<String> values) {
            addCriterion("startDate not in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateBetween(String value1, String value2) {
            addCriterion("startDate between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotBetween(String value1, String value2) {
            addCriterion("startDate not between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andUpdateatIsNull() {
            addCriterion("updateAt is null");
            return (Criteria) this;
        }

        public Criteria andUpdateatIsNotNull() {
            addCriterion("updateAt is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateatEqualTo(String value) {
            addCriterion("updateAt =", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatNotEqualTo(String value) {
            addCriterion("updateAt <>", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatGreaterThan(String value) {
            addCriterion("updateAt >", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatGreaterThanOrEqualTo(String value) {
            addCriterion("updateAt >=", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatLessThan(String value) {
            addCriterion("updateAt <", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatLessThanOrEqualTo(String value) {
            addCriterion("updateAt <=", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatLike(String value) {
            addCriterion("updateAt like", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatNotLike(String value) {
            addCriterion("updateAt not like", value, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatIn(List<String> values) {
            addCriterion("updateAt in", values, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatNotIn(List<String> values) {
            addCriterion("updateAt not in", values, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatBetween(String value1, String value2) {
            addCriterion("updateAt between", value1, value2, "updateat");
            return (Criteria) this;
        }

        public Criteria andUpdateatNotBetween(String value1, String value2) {
            addCriterion("updateAt not between", value1, value2, "updateat");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNull() {
            addCriterion("endDate is null");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNotNull() {
            addCriterion("endDate is not null");
            return (Criteria) this;
        }

        public Criteria andEnddateEqualTo(String value) {
            addCriterion("endDate =", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotEqualTo(String value) {
            addCriterion("endDate <>", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThan(String value) {
            addCriterion("endDate >", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThanOrEqualTo(String value) {
            addCriterion("endDate >=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThan(String value) {
            addCriterion("endDate <", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThanOrEqualTo(String value) {
            addCriterion("endDate <=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLike(String value) {
            addCriterion("endDate like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotLike(String value) {
            addCriterion("endDate not like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateIn(List<String> values) {
            addCriterion("endDate in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotIn(List<String> values) {
            addCriterion("endDate not in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateBetween(String value1, String value2) {
            addCriterion("endDate between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotBetween(String value1, String value2) {
            addCriterion("endDate not between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andProgressIsNull() {
            addCriterion("progress is null");
            return (Criteria) this;
        }

        public Criteria andProgressIsNotNull() {
            addCriterion("progress is not null");
            return (Criteria) this;
        }

        public Criteria andProgressEqualTo(Integer value) {
            addCriterion("progress =", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotEqualTo(Integer value) {
            addCriterion("progress <>", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThan(Integer value) {
            addCriterion("progress >", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThanOrEqualTo(Integer value) {
            addCriterion("progress >=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThan(Integer value) {
            addCriterion("progress <", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThanOrEqualTo(Integer value) {
            addCriterion("progress <=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressIn(List<Integer> values) {
            addCriterion("progress in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotIn(List<Integer> values) {
            addCriterion("progress not in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressBetween(Integer value1, Integer value2) {
            addCriterion("progress between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotBetween(Integer value1, Integer value2) {
            addCriterion("progress not between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressatIsNull() {
            addCriterion("progressAt is null");
            return (Criteria) this;
        }

        public Criteria andProgressatIsNotNull() {
            addCriterion("progressAt is not null");
            return (Criteria) this;
        }

        public Criteria andProgressatEqualTo(String value) {
            addCriterion("progressAt =", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatNotEqualTo(String value) {
            addCriterion("progressAt <>", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatGreaterThan(String value) {
            addCriterion("progressAt >", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatGreaterThanOrEqualTo(String value) {
            addCriterion("progressAt >=", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatLessThan(String value) {
            addCriterion("progressAt <", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatLessThanOrEqualTo(String value) {
            addCriterion("progressAt <=", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatLike(String value) {
            addCriterion("progressAt like", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatNotLike(String value) {
            addCriterion("progressAt not like", value, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatIn(List<String> values) {
            addCriterion("progressAt in", values, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatNotIn(List<String> values) {
            addCriterion("progressAt not in", values, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatBetween(String value1, String value2) {
            addCriterion("progressAt between", value1, value2, "progressat");
            return (Criteria) this;
        }

        public Criteria andProgressatNotBetween(String value1, String value2) {
            addCriterion("progressAt not between", value1, value2, "progressat");
            return (Criteria) this;
        }

        public Criteria andPosterimageIsNull() {
            addCriterion("posterImage is null");
            return (Criteria) this;
        }

        public Criteria andPosterimageIsNotNull() {
            addCriterion("posterImage is not null");
            return (Criteria) this;
        }

        public Criteria andPosterimageEqualTo(String value) {
            addCriterion("posterImage =", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageNotEqualTo(String value) {
            addCriterion("posterImage <>", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageGreaterThan(String value) {
            addCriterion("posterImage >", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageGreaterThanOrEqualTo(String value) {
            addCriterion("posterImage >=", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageLessThan(String value) {
            addCriterion("posterImage <", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageLessThanOrEqualTo(String value) {
            addCriterion("posterImage <=", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageLike(String value) {
            addCriterion("posterImage like", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageNotLike(String value) {
            addCriterion("posterImage not like", value, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageIn(List<String> values) {
            addCriterion("posterImage in", values, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageNotIn(List<String> values) {
            addCriterion("posterImage not in", values, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageBetween(String value1, String value2) {
            addCriterion("posterImage between", value1, value2, "posterimage");
            return (Criteria) this;
        }

        public Criteria andPosterimageNotBetween(String value1, String value2) {
            addCriterion("posterImage not between", value1, value2, "posterimage");
            return (Criteria) this;
        }

        public Criteria andRatingrankIsNull() {
            addCriterion("ratingRank is null");
            return (Criteria) this;
        }

        public Criteria andRatingrankIsNotNull() {
            addCriterion("ratingRank is not null");
            return (Criteria) this;
        }

        public Criteria andRatingrankEqualTo(String value) {
            addCriterion("ratingRank =", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankNotEqualTo(String value) {
            addCriterion("ratingRank <>", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankGreaterThan(String value) {
            addCriterion("ratingRank >", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankGreaterThanOrEqualTo(String value) {
            addCriterion("ratingRank >=", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankLessThan(String value) {
            addCriterion("ratingRank <", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankLessThanOrEqualTo(String value) {
            addCriterion("ratingRank <=", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankLike(String value) {
            addCriterion("ratingRank like", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankNotLike(String value) {
            addCriterion("ratingRank not like", value, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankIn(List<String> values) {
            addCriterion("ratingRank in", values, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankNotIn(List<String> values) {
            addCriterion("ratingRank not in", values, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankBetween(String value1, String value2) {
            addCriterion("ratingRank between", value1, value2, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andRatingrankNotBetween(String value1, String value2) {
            addCriterion("ratingRank not between", value1, value2, "ratingrank");
            return (Criteria) this;
        }

        public Criteria andAverageratingIsNull() {
            addCriterion("averageRating is null");
            return (Criteria) this;
        }

        public Criteria andAverageratingIsNotNull() {
            addCriterion("averageRating is not null");
            return (Criteria) this;
        }

        public Criteria andAverageratingEqualTo(String value) {
            addCriterion("averageRating =", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingNotEqualTo(String value) {
            addCriterion("averageRating <>", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingGreaterThan(String value) {
            addCriterion("averageRating >", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingGreaterThanOrEqualTo(String value) {
            addCriterion("averageRating >=", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingLessThan(String value) {
            addCriterion("averageRating <", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingLessThanOrEqualTo(String value) {
            addCriterion("averageRating <=", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingLike(String value) {
            addCriterion("averageRating like", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingNotLike(String value) {
            addCriterion("averageRating not like", value, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingIn(List<String> values) {
            addCriterion("averageRating in", values, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingNotIn(List<String> values) {
            addCriterion("averageRating not in", values, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingBetween(String value1, String value2) {
            addCriterion("averageRating between", value1, value2, "averagerating");
            return (Criteria) this;
        }

        public Criteria andAverageratingNotBetween(String value1, String value2) {
            addCriterion("averageRating not between", value1, value2, "averagerating");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andMystatusIsNull() {
            addCriterion("myStatus is null");
            return (Criteria) this;
        }

        public Criteria andMystatusIsNotNull() {
            addCriterion("myStatus is not null");
            return (Criteria) this;
        }

        public Criteria andMystatusEqualTo(String value) {
            addCriterion("myStatus =", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusNotEqualTo(String value) {
            addCriterion("myStatus <>", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusGreaterThan(String value) {
            addCriterion("myStatus >", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusGreaterThanOrEqualTo(String value) {
            addCriterion("myStatus >=", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusLessThan(String value) {
            addCriterion("myStatus <", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusLessThanOrEqualTo(String value) {
            addCriterion("myStatus <=", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusLike(String value) {
            addCriterion("myStatus like", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusNotLike(String value) {
            addCriterion("myStatus not like", value, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusIn(List<String> values) {
            addCriterion("myStatus in", values, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusNotIn(List<String> values) {
            addCriterion("myStatus not in", values, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusBetween(String value1, String value2) {
            addCriterion("myStatus between", value1, value2, "mystatus");
            return (Criteria) this;
        }

        public Criteria andMystatusNotBetween(String value1, String value2) {
            addCriterion("myStatus not between", value1, value2, "mystatus");
            return (Criteria) this;
        }

        public Criteria andChaptercountIsNull() {
            addCriterion("chapterCount is null");
            return (Criteria) this;
        }

        public Criteria andChaptercountIsNotNull() {
            addCriterion("chapterCount is not null");
            return (Criteria) this;
        }

        public Criteria andChaptercountEqualTo(String value) {
            addCriterion("chapterCount =", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountNotEqualTo(String value) {
            addCriterion("chapterCount <>", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountGreaterThan(String value) {
            addCriterion("chapterCount >", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountGreaterThanOrEqualTo(String value) {
            addCriterion("chapterCount >=", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountLessThan(String value) {
            addCriterion("chapterCount <", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountLessThanOrEqualTo(String value) {
            addCriterion("chapterCount <=", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountLike(String value) {
            addCriterion("chapterCount like", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountNotLike(String value) {
            addCriterion("chapterCount not like", value, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountIn(List<String> values) {
            addCriterion("chapterCount in", values, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountNotIn(List<String> values) {
            addCriterion("chapterCount not in", values, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountBetween(String value1, String value2) {
            addCriterion("chapterCount between", value1, value2, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andChaptercountNotBetween(String value1, String value2) {
            addCriterion("chapterCount not between", value1, value2, "chaptercount");
            return (Criteria) this;
        }

        public Criteria andProgressStrIsNull() {
            addCriterion("progress_str is null");
            return (Criteria) this;
        }

        public Criteria andProgressStrIsNotNull() {
            addCriterion("progress_str is not null");
            return (Criteria) this;
        }

        public Criteria andProgressStrEqualTo(String value) {
            addCriterion("progress_str =", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrNotEqualTo(String value) {
            addCriterion("progress_str <>", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrGreaterThan(String value) {
            addCriterion("progress_str >", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrGreaterThanOrEqualTo(String value) {
            addCriterion("progress_str >=", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrLessThan(String value) {
            addCriterion("progress_str <", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrLessThanOrEqualTo(String value) {
            addCriterion("progress_str <=", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrLike(String value) {
            addCriterion("progress_str like", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrNotLike(String value) {
            addCriterion("progress_str not like", value, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrIn(List<String> values) {
            addCriterion("progress_str in", values, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrNotIn(List<String> values) {
            addCriterion("progress_str not in", values, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrBetween(String value1, String value2) {
            addCriterion("progress_str between", value1, value2, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressStrNotBetween(String value1, String value2) {
            addCriterion("progress_str not between", value1, value2, "progressStr");
            return (Criteria) this;
        }

        public Criteria andProgressWidthIsNull() {
            addCriterion("progress_width is null");
            return (Criteria) this;
        }

        public Criteria andProgressWidthIsNotNull() {
            addCriterion("progress_width is not null");
            return (Criteria) this;
        }

        public Criteria andProgressWidthEqualTo(String value) {
            addCriterion("progress_width =", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthNotEqualTo(String value) {
            addCriterion("progress_width <>", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthGreaterThan(String value) {
            addCriterion("progress_width >", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthGreaterThanOrEqualTo(String value) {
            addCriterion("progress_width >=", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthLessThan(String value) {
            addCriterion("progress_width <", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthLessThanOrEqualTo(String value) {
            addCriterion("progress_width <=", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthLike(String value) {
            addCriterion("progress_width like", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthNotLike(String value) {
            addCriterion("progress_width not like", value, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthIn(List<String> values) {
            addCriterion("progress_width in", values, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthNotIn(List<String> values) {
            addCriterion("progress_width not in", values, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthBetween(String value1, String value2) {
            addCriterion("progress_width between", value1, value2, "progressWidth");
            return (Criteria) this;
        }

        public Criteria andProgressWidthNotBetween(String value1, String value2) {
            addCriterion("progress_width not between", value1, value2, "progressWidth");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}