//package com.teamwork.integrationproject.common.repository.orm;
//
//import com.teamwork.integrationproject.common.repository.IRepository;
//import com.teamwork.integrationproject.utils.log.LogHelper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//import org.springframework.util.StringUtils;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//public class OracleOrmRepository<M, K> implements IRepository<M, K>
//{
//    /**
//     * 可以供子类使用，以满足其他需求
//     */
//    @Autowired
//    protected JdbcTemplate dao;
//
//    @Override
//    public void save(M model) throws Exception
//    {
//        if (model == null)
//        {
//            throw new IllegalArgumentException("model can not be null!");
//        }
//
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(model.getClass());
//        String sql = buildInsertSql(entityOrmInfo);
//        Object[] fieldValues = extractFieldValue(entityOrmInfo.getFieldInfoList(), model);
//        dao.update(sql, fieldValues);
//    }
//
//    @Override
//    public void save(List<M> modelList) throws Exception
//    {
//        if (modelList == null || modelList.size() < 1)
//        {
//            throw new IllegalArgumentException("model list can not be null or empty!");
//        }
//
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(modelList.get(0).getClass());
//        String sql = buildInsertSql(entityOrmInfo);
//        List<FieldOrmInfo> fieldInfoList = entityOrmInfo.getFieldInfoList();
//
//        dao.batchUpdate(sql, new BatchPreparedStatementSetter()
//        {
//            @Override
//            public void setValues(PreparedStatement ps, int index) throws SQLException
//            {
//                M model = modelList.get(index);
//
//                for (int i = 0; i < fieldInfoList.size(); i++)
//                {
//                    FieldOrmInfo fieldOrmInfo = fieldInfoList.get(i);
//                    try
//                    {
//                        ps.setObject(i + 1, fieldOrmInfo.getValue(model));
//                    }
//                    catch (Exception e)
//                    {
//                        LogHelper.error(this, "set param index {} from {} error",
//                                fieldOrmInfo, e);
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public int getBatchSize()
//            {
//                return modelList.size();
//            }
//        });
//    }
//
//    @Override
//    public void update(M model) throws Exception
//    {
//        if (model == null)
//        {
//            throw new IllegalArgumentException("model can not be null!");
//        }
//
//        //update t_user set u_id=?, u_username=? where u_id=?
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(model.getClass());
//        StringBuilder updateSql = new StringBuilder("update ");
//        updateSql.append(entityOrmInfo.getTableName());
//
//        StringBuilder setPart = new StringBuilder(" set ");
//        StringBuilder log = new StringBuilder("values:");
//        List<FieldOrmInfo> fieldInfoList = entityOrmInfo.getFieldInfoList();
//        Object[] fieldValues = new Object[fieldInfoList.size() + 1];
//        for (int index = 0; index < fieldInfoList.size(); index++)
//        {
//            if (index > 0)
//            {
//                setPart.append(", ");
//                log.append(", ");
//            }
//
//            FieldOrmInfo fieldOrmInfo = fieldInfoList.get(index);
//            setPart.append(fieldOrmInfo.getColumnName()).append("=?");
//            log.append("{}");
//            fieldValues[index] = fieldOrmInfo.getValue(model);
//        }
//        FieldOrmInfo keyFieldInfo = entityOrmInfo.getKeyFieldInfo();
//        updateSql.append(setPart).append(" where ")
//                .append(keyFieldInfo.getColumnName()).append("=?");
//        log.append(", {}");
//        fieldValues[fieldInfoList.size()] = keyFieldInfo.getValue(model);
//        String sql = updateSql.toString();
//
//        LogHelper.info(this, sql);
//        LogHelper.info(this, log.toString(), fieldValues);
//
//        dao.update(sql, fieldValues);
//    }
//
//    @Override
//    public void delete(M model) throws Exception
//    {
//        if (model == null)
//        {
//            throw new IllegalArgumentException("model can not be null!");
//        }
//
//        //delete from t_user where u_id = ?
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(model.getClass());
//        StringBuilder deleteSql = new StringBuilder("delete from ");
//        deleteSql.append(entityOrmInfo.getTableName()).append(" where ");
//
//        FieldOrmInfo keyFieldInfo = entityOrmInfo.getKeyFieldInfo();
//        deleteSql.append(keyFieldInfo.getColumnName()).append("=?");
//
//        String sql = deleteSql.toString();
//        LogHelper.info(this, sql);
//        Object id = keyFieldInfo.getValue(model);
//        LogHelper.info(this, "id:{}", id);
//
//        dao.update(sql, id);
//    }
//
//    @Override
//    public void deleteByPrimaryKey(Class<M> clazz, K key)
//    {
//        if (key == null)
//        {
//            throw new IllegalArgumentException("key can not be null!");
//        }
//
//        //delete from t_user where u_id = ?
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(clazz);
//        StringBuilder deleteSql = new StringBuilder("delete from ");
//        deleteSql.append(entityOrmInfo.getTableName()).append(" where ");
//
//        FieldOrmInfo keyFieldInfo = entityOrmInfo.getKeyFieldInfo();
//        deleteSql.append(keyFieldInfo.getColumnName()).append("=?");
//
//        String sql = deleteSql.toString();
//        LogHelper.info(this, sql);
//        LogHelper.info(this, "id:{}", key);
//
//        dao.update(sql, key);
//    }
//
//    @Override
//    public M selectByPrimaryKey(Class<M> clazz, K key) throws Exception
//    {
//        //select * from t_user where u_id = ?
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(clazz);
//        StringBuilder selectSql = new StringBuilder("select * from ");
//        selectSql.append(entityOrmInfo.getTableName()).append(" where ");
//        FieldOrmInfo keyFieldInfo = entityOrmInfo.getKeyFieldInfo();
//        selectSql.append(keyFieldInfo.getColumnName()).append("=?");
//
//        String sql = selectSql.toString();
//        LogHelper.info(this, sql);
//        LogHelper.info(this, "id:{}", key);
//
//        SqlRowSet rowSet = dao.queryForRowSet(sql, key);
//        List<M> result = transnate(clazz, rowSet);
//
//        return result.size() > 0 ? result.get(0) : null;
//    }
//
//    @Override
//    public <T> List<T> selectByNative(Class<T> clazz, String sql, Object... params) throws Exception
//    {
//        if (StringUtils.isEmpty(sql))
//        {
//            throw new IllegalArgumentException("sql can not be empty!");
//        }
//
//        LogHelper.info(this, sql);
//        LogHelper.info(this, "params: {}", Arrays.stream(params)
//                .map(x -> x.toString()).collect(Collectors.joining(", ")));
//
//        SqlRowSet rowSet = dao.queryForRowSet(sql, params);
//
//        return transnate(clazz, rowSet);
//    }
//
//    public <T> Page<T> selectByPageNative(Class<T> clazz, int current, int pageSize, int totalCount, String sql, Object... params) throws Exception
//    {
//        LogHelper.info(this, "selectByPage: current={}, pageSize={}, totalCount={}", current, pageSize, totalCount);
//        if (current <= 0 || pageSize <= 0)
//        {
//            throw new IllegalArgumentException("curent and pageSize should be > 0 !");
//        }
//
//        if (totalCount < -1)
//        {
//            throw new IllegalArgumentException("totalCount should be -1 or >= 0 !");
//        }
//
//        //select count(*) from (select * from t_user);
//        //select a.*  from (select t.*, rownum rowno from (select * from t_user) t where rownum <= 20) a where a
//        // .rowno >= 11;
//        //查询总量
//        if (totalCount == -1)
//        {
//            StringBuilder selectCountSql = new StringBuilder("select count(*) from (");
//            selectCountSql.append(sql).append(")");
//            String countSql = selectCountSql.toString();
//            LogHelper.info(this, "selectByPage: count sql= {}", countSql);
//            SqlRowSet rowSet = dao.queryForRowSet(countSql, params);
//            rowSet.next();
//            totalCount = rowSet.getInt(1);
//            LogHelper.info(this, "selectByPage: count value = {}", totalCount);
//        }
//
//        List<T> dataList = null;
//        int totalPage = 0;
//        if (totalCount > 0)
//        {
//            totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :
//                    totalCount < pageSize ? 1 : totalCount / pageSize + 1;
//            StringBuilder pageSql = new StringBuilder("select a.*  from (select t.*, rownum rowno from (");
//            pageSql.append(sql).append(")").append(" t where rownum <= ")
//                    .append(current * pageSize);
//            pageSql.append(") a where a.rowno >= ").append((current - 1) * pageSize + 1);
//            String sqlbyPage = pageSql.toString();
//            LogHelper.info(this, "selectByPage: page sql= {}", sqlbyPage);
//            SqlRowSet rowSet = dao.queryForRowSet(sqlbyPage, params);
//            dataList = transnate(clazz, rowSet);
//        }
//
//        return new Page<T>(current, pageSize, totalCount, totalPage, dataList);
//    }
//
//    @Override
//    public <T> Page<T> selectByPage(Class<T> clazz, int current, int pageSize, int totalCount, String orderBy) throws Exception
//    {
//        LogHelper.info(this, "selectByPage: current={}, pageSize={}, totalCount={}", current, pageSize, totalCount);
//
//        if (current <= 0 || pageSize <= 0)
//        {
//            throw new IllegalArgumentException("curent and pageSize should be > 0 !");
//        }
//
//        if (totalCount < -1)
//        {
//            throw new IllegalArgumentException("totalCount should be -1 or >= 0 !");
//        }
//        //select count(*) from t_user;
//        //select a.*  from (select t.*, rownum rowno from t_user t where rownum <= 20) a where a.rowno >= 11;
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(clazz);
//
//        //查询总量
//        if (totalCount == -1)
//        {
//            StringBuilder selectCountSql = new StringBuilder("select count(*) from ");
//            selectCountSql.append(entityOrmInfo.getTableName());
//            String sql = selectCountSql.toString();
//            LogHelper.info(this, "selectByPage: total sql= {}", sql);
//            SqlRowSet rowSet = dao.queryForRowSet(sql);
//            rowSet.next();
//            totalCount = rowSet.getInt(1);
//            LogHelper.info(this, "selectByPage: total value = {}", totalCount);
//        }
//
//        List<T> dataList = null;
//        int totalPage = 0;
//        if (totalCount > 0)
//        {
//            totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :
//                    totalCount < pageSize ? 1 : totalCount / pageSize + 1;
//            StringBuilder pageSql = new StringBuilder("select a.*  from (select t.*, rownum rowno from ");
//            pageSql.append(entityOrmInfo.getTableName()).append(" t where rownum <= ")
//                    .append(current * pageSize);
//            if (!StringUtils.isEmpty(orderBy))
//            {
//                pageSql.append(" ").append(orderBy);
//            }
//            pageSql.append(") a where a.rowno >= ").append((current - 1) * pageSize + 1);
//            String sql = pageSql.toString();
//            LogHelper.info(this, "selectByPage: page sql= {}", sql);
//            SqlRowSet rowSet = dao.queryForRowSet(sql);
//            dataList = transnate(clazz, rowSet);
//        }
//
//        return new Page<T>(current, pageSize, totalCount, totalPage, dataList);
//    }
//
//    @Override
//    public List<M> selectAll(Class<M> clazz) throws Exception
//    {
//        //select * from t_user
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(clazz);
//        StringBuilder selectSql = new StringBuilder("select * from ");
//        selectSql.append(entityOrmInfo.getTableName());
//
//        String sql = selectSql.toString();
//        LogHelper.info(this, sql);
//
//        SqlRowSet rowSet = dao.queryForRowSet(sql);
//
//        return transnate(clazz, rowSet);
//    }
//
//    private Object[] extractFieldValue(List<FieldOrmInfo> fieldInfoList, M model) throws Exception
//    {
//        Object[] fieldValues = new Object[fieldInfoList.size()];
//        StringBuilder log = new StringBuilder("values:");
//        for (int index = 0; index < fieldInfoList.size(); index++)
//        {
//            if (index > 0)
//            {
//                log.append(", ");
//            }
//
//            log.append("{}");
//            FieldOrmInfo fieldOrmInfo = fieldInfoList.get(index);
//            fieldValues[index] = fieldOrmInfo.getValue(model);
//        }
//
//        LogHelper.info(this, log.toString(), fieldValues);
//        return fieldValues;
//    }
//
//    private String buildInsertSql(EntityOrmInfo entityOrmInfo) throws Exception
//    {
//        //insert into t_user(u_id,u_username) values(?,?)
//        StringBuilder insertSql = new StringBuilder("insert into ");
//        insertSql.append(entityOrmInfo.getTableName());
//
//        List<FieldOrmInfo> fieldInfoList = entityOrmInfo.getFieldInfoList();
//        StringBuilder columnPart = new StringBuilder("(");
//        StringBuilder valuesPart = new StringBuilder("values(");
//
//        fieldInfoList.add(entityOrmInfo.getKeyFieldInfo());
//        for (int index = 0; index < fieldInfoList.size(); index++)
//        {
//            if (index > 0)
//            {
//                columnPart.append(", ");
//                valuesPart.append(", ");
//            }
//
//            FieldOrmInfo fieldOrmInfo = fieldInfoList.get(index);
//            columnPart.append(fieldOrmInfo.getColumnName());
//            valuesPart.append("?");
//        }
//
//        columnPart.append(")");
//        valuesPart.append(")");
//        insertSql.append(columnPart).append(" ").append(valuesPart);
//        String sql = insertSql.toString();
//        LogHelper.info(this, sql);
//
//        return sql;
//    }
//
//    public  <M> List<M> transnate(Class<M> clazz, SqlRowSet rowSet) throws Exception
//    {
//        if (rowSet == null)
//        {
//            return null;
//        }
//
//        EntityOrmInfo entityOrmInfo = EntityOrmInfo.from(clazz);
//        List<FieldOrmInfo> fieldInfoList = entityOrmInfo.getFieldInfoList();
//        fieldInfoList.add(entityOrmInfo.getKeyFieldInfo());
//
//        List<M> modelList = new ArrayList<>();
//        while (rowSet.next())
//        {
//            M model = null;
//            try
//            {
//                model = clazz.newInstance();
//            }
//            catch (Exception e)
//            {
//                throw new IllegalArgumentException("must have a public no-argument constructor!");
//            }
//
//            for (FieldOrmInfo fieldOrmInfo : fieldInfoList)
//            {
//                fieldOrmInfo.setValue(rowSet, model);
//            }
//
//            modelList.add(model);
//        }
//
//        return modelList.size() > 0 ? modelList : Collections.emptyList();
//    }
//
//    public Page selectByPageNativeForMap(int current, int pageSize, int totalCount, String sql, Object... params) throws Exception
//    {
//        LogHelper.info(this, "selectByPage: current={}, pageSize={}, totalCount={}", new Object[]{current, pageSize, totalCount});
//        if (current > 0 && pageSize > 0) {
//            if (totalCount < -1) {
//                throw new IllegalArgumentException("totalCount should be -1 or >= 0 !");
//            } else {
//                if (totalCount == -1) {
//                    StringBuilder selectCountSql = new StringBuilder("select count(*) from (");
//                    selectCountSql.append(sql).append(")");
//                    String countSql = selectCountSql.toString();
//                    LogHelper.info(this, "selectByPage: count sql= {}", countSql);
//                    SqlRowSet rowSet = this.dao.queryForRowSet(countSql, params);
//                    rowSet.next();
//                    totalCount = rowSet.getInt(1);
//                    LogHelper.info(this, "selectByPage: count value = {}", totalCount);
//                }
//
//                List<Map<String, Object>> maps = null;
//                int totalPage = 0;
//                if (totalCount > 0) {
//                    totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount < pageSize ? 1 : totalCount / pageSize + 1);
//                    StringBuilder pageSql = new StringBuilder("select a.*  from (select t.*, rownum rowno from (");
//                    pageSql.append(sql).append(")").append(" t where rownum <= ").append(current * pageSize);
//                    pageSql.append(") a where a.rowno >= ").append((current - 1) * pageSize + 1);
//                    String sqlbyPage = pageSql.toString();
//                    LogHelper.info(this, "selectByPage: page sql= {}", sqlbyPage);
//                    maps = this.dao.queryForList(sqlbyPage, params);
//                }
//                return new Page(current, pageSize, totalCount, totalPage,maps);
//            }
//        } else {
//            throw new IllegalArgumentException("curent and pageSize should be > 0 !");
//        }
//    }
//}
