package com.Lhan.personal_blog.pojo;

import java.util.ArrayList;
import java.util.List;

public class CommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommentExample() {
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Long value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Long value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Long value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Long value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Long value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Long value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Long> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Long> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Long value1, Long value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Long value1, Long value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andCommentDateIsNull() {
            addCriterion("comment_date is null");
            return (Criteria) this;
        }

        public Criteria andCommentDateIsNotNull() {
            addCriterion("comment_date is not null");
            return (Criteria) this;
        }

        public Criteria andCommentDateEqualTo(String value) {
            addCriterion("comment_date =", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateNotEqualTo(String value) {
            addCriterion("comment_date <>", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateGreaterThan(String value) {
            addCriterion("comment_date >", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateGreaterThanOrEqualTo(String value) {
            addCriterion("comment_date >=", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateLessThan(String value) {
            addCriterion("comment_date <", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateLessThanOrEqualTo(String value) {
            addCriterion("comment_date <=", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateLike(String value) {
            addCriterion("comment_date like", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateNotLike(String value) {
            addCriterion("comment_date not like", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateIn(List<String> values) {
            addCriterion("comment_date in", values, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateNotIn(List<String> values) {
            addCriterion("comment_date not in", values, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateBetween(String value1, String value2) {
            addCriterion("comment_date between", value1, value2, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateNotBetween(String value1, String value2) {
            addCriterion("comment_date not between", value1, value2, "commentDate");
            return (Criteria) this;
        }

        public Criteria andAnswerIdIsNull() {
            addCriterion("answer_id is null");
            return (Criteria) this;
        }

        public Criteria andAnswerIdIsNotNull() {
            addCriterion("answer_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerIdEqualTo(Long value) {
            addCriterion("answer_id =", value, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdNotEqualTo(Long value) {
            addCriterion("answer_id <>", value, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdGreaterThan(Long value) {
            addCriterion("answer_id >", value, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("answer_id >=", value, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdLessThan(Long value) {
            addCriterion("answer_id <", value, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdLessThanOrEqualTo(Long value) {
            addCriterion("answer_id <=", value, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdIn(List<Long> values) {
            addCriterion("answer_id in", values, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdNotIn(List<Long> values) {
            addCriterion("answer_id not in", values, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdBetween(Long value1, Long value2) {
            addCriterion("answer_id between", value1, value2, "answerId");
            return (Criteria) this;
        }

        public Criteria andAnswerIdNotBetween(Long value1, Long value2) {
            addCriterion("answer_id not between", value1, value2, "answerId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdIsNull() {
            addCriterion("respondent_id is null");
            return (Criteria) this;
        }

        public Criteria andRespondentIdIsNotNull() {
            addCriterion("respondent_id is not null");
            return (Criteria) this;
        }

        public Criteria andRespondentIdEqualTo(Long value) {
            addCriterion("respondent_id =", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdNotEqualTo(Long value) {
            addCriterion("respondent_id <>", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdGreaterThan(Long value) {
            addCriterion("respondent_id >", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("respondent_id >=", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdLessThan(Long value) {
            addCriterion("respondent_id <", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdLessThanOrEqualTo(Long value) {
            addCriterion("respondent_id <=", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdIn(List<Long> values) {
            addCriterion("respondent_id in", values, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdNotIn(List<Long> values) {
            addCriterion("respondent_id not in", values, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdBetween(Long value1, Long value2) {
            addCriterion("respondent_id between", value1, value2, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdNotBetween(Long value1, Long value2) {
            addCriterion("respondent_id not between", value1, value2, "respondentId");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveIsNull() {
            addCriterion("is_effective is null");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveIsNotNull() {
            addCriterion("is_effective is not null");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveEqualTo(Boolean value) {
            addCriterion("is_effective =", value, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveNotEqualTo(Boolean value) {
            addCriterion("is_effective <>", value, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveGreaterThan(Boolean value) {
            addCriterion("is_effective >", value, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_effective >=", value, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveLessThan(Boolean value) {
            addCriterion("is_effective <", value, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveLessThanOrEqualTo(Boolean value) {
            addCriterion("is_effective <=", value, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveIn(List<Boolean> values) {
            addCriterion("is_effective in", values, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveNotIn(List<Boolean> values) {
            addCriterion("is_effective not in", values, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveBetween(Boolean value1, Boolean value2) {
            addCriterion("is_effective between", value1, value2, "isEffective");
            return (Criteria) this;
        }

        public Criteria andIsEffectiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_effective not between", value1, value2, "isEffective");
            return (Criteria) this;
        }

        public Criteria andLikesIsNull() {
            addCriterion("likes is null");
            return (Criteria) this;
        }

        public Criteria andLikesIsNotNull() {
            addCriterion("likes is not null");
            return (Criteria) this;
        }

        public Criteria andLikesEqualTo(Integer value) {
            addCriterion("likes =", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotEqualTo(Integer value) {
            addCriterion("likes <>", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesGreaterThan(Integer value) {
            addCriterion("likes >", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesGreaterThanOrEqualTo(Integer value) {
            addCriterion("likes >=", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesLessThan(Integer value) {
            addCriterion("likes <", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesLessThanOrEqualTo(Integer value) {
            addCriterion("likes <=", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesIn(List<Integer> values) {
            addCriterion("likes in", values, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotIn(List<Integer> values) {
            addCriterion("likes not in", values, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesBetween(Integer value1, Integer value2) {
            addCriterion("likes between", value1, value2, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotBetween(Integer value1, Integer value2) {
            addCriterion("likes not between", value1, value2, "likes");
            return (Criteria) this;
        }

        public Criteria andUserAgentIsNull() {
            addCriterion("user_agent is null");
            return (Criteria) this;
        }

        public Criteria andUserAgentIsNotNull() {
            addCriterion("user_agent is not null");
            return (Criteria) this;
        }

        public Criteria andUserAgentEqualTo(String value) {
            addCriterion("user_agent =", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotEqualTo(String value) {
            addCriterion("user_agent <>", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentGreaterThan(String value) {
            addCriterion("user_agent >", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentGreaterThanOrEqualTo(String value) {
            addCriterion("user_agent >=", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentLessThan(String value) {
            addCriterion("user_agent <", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentLessThanOrEqualTo(String value) {
            addCriterion("user_agent <=", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentLike(String value) {
            addCriterion("user_agent like", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotLike(String value) {
            addCriterion("user_agent not like", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentIn(List<String> values) {
            addCriterion("user_agent in", values, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotIn(List<String> values) {
            addCriterion("user_agent not in", values, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentBetween(String value1, String value2) {
            addCriterion("user_agent between", value1, value2, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotBetween(String value1, String value2) {
            addCriterion("user_agent not between", value1, value2, "userAgent");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNull() {
            addCriterion("is_read is null");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNotNull() {
            addCriterion("is_read is not null");
            return (Criteria) this;
        }

        public Criteria andIsReadEqualTo(Boolean value) {
            addCriterion("is_read =", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotEqualTo(Boolean value) {
            addCriterion("is_read <>", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThan(Boolean value) {
            addCriterion("is_read >", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_read >=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThan(Boolean value) {
            addCriterion("is_read <", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThanOrEqualTo(Boolean value) {
            addCriterion("is_read <=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadIn(List<Boolean> values) {
            addCriterion("is_read in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotIn(List<Boolean> values) {
            addCriterion("is_read not in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadBetween(Boolean value1, Boolean value2) {
            addCriterion("is_read between", value1, value2, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_read not between", value1, value2, "isRead");
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