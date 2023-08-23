package io.seata.sqlparser.druid.dm;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import io.seata.sqlparser.ParametersHolder;
import io.seata.sqlparser.SQLParsingException;
import io.seata.sqlparser.SQLSelectRecognizer;
import io.seata.sqlparser.SQLType;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 *
 * @Description:
 * @Author: Cheng XiaoXiao
 * @Date: 2022-5-12 18:5
 */
public class DmSelectForUpdateRecognizer extends BaseDmRecognizer implements SQLSelectRecognizer {

    private final SQLSelectStatement ast;

    /**
     * Instantiates a new My sql select for update recognizer.
     *
     * @param originalSQL the original sql
     * @param ast         the ast
     */
    public DmSelectForUpdateRecognizer(String originalSQL, SQLStatement ast) {
        super(originalSQL);
        this.ast = (SQLSelectStatement)ast;
    }

    @Override
    public SQLType getSQLType() {
        return SQLType.SELECT_FOR_UPDATE;
    }

    @Override
    public String getWhereCondition(final ParametersHolder parametersHolder,
                                    final ArrayList<List<Object>> paramAppenderList) {
        SQLSelectQueryBlock selectQueryBlock = getSelect();
        SQLExpr where = selectQueryBlock.getWhere();
        return super.getWhereCondition(where, parametersHolder, paramAppenderList);
    }

    @Override
    public String getWhereCondition() {
        SQLSelectQueryBlock selectQueryBlock = getSelect();
        SQLExpr where = selectQueryBlock.getWhere();
        return super.getWhereCondition(where);
    }

    private SQLSelectQueryBlock getSelect() {
        SQLSelect select = ast.getSelect();
        if (select == null) {
            throw new SQLParsingException("should never happen!");
        }
        SQLSelectQueryBlock selectQueryBlock = select.getQueryBlock();
        if (selectQueryBlock == null) {
            throw new SQLParsingException("should never happen!");
        }
        return selectQueryBlock;
    }

    @Override
    public String getTableAlias() {
        SQLSelectQueryBlock selectQueryBlock = getSelect();
        SQLTableSource tableSource = selectQueryBlock.getFrom();
        return tableSource.getAlias();
    }

    @Override
    public String getTableName() {
        SQLSelectQueryBlock selectQueryBlock = getSelect();
        SQLTableSource tableSource = selectQueryBlock.getFrom();
        StringBuilder sb = new StringBuilder();
        OracleOutputVisitor visitor = new OracleOutputVisitor(sb) {

            @Override
            public boolean visit(SQLExprTableSource x) {
                printTableSourceExpr(x.getExpr());
                return false;
            }
        };
        visitor.visit((SQLExprTableSource)tableSource);
        return sb.toString();
    }

}
