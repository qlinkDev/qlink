package com.stratagile.qlink.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BUY_SELL_SELL_TODO".
*/
public class BuySellSellTodoDao extends AbstractDao<BuySellSellTodo, Long> {

    public static final String TABLENAME = "BUY_SELL_SELL_TODO";

    /**
     * Properties of entity BuySellSellTodo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public final static Property Token = new Property(2, String.class, "token", false, "TOKEN");
        public final static Property EntrustOrderId = new Property(3, String.class, "entrustOrderId", false, "ENTRUST_ORDER_ID");
        public final static Property UsdtAmount = new Property(4, String.class, "usdtAmount", false, "USDT_AMOUNT");
        public final static Property UsdtToAddress = new Property(5, String.class, "usdtToAddress", false, "USDT_TO_ADDRESS");
        public final static Property QgasAmount = new Property(6, String.class, "qgasAmount", false, "QGAS_AMOUNT");
        public final static Property FromAddress = new Property(7, String.class, "fromAddress", false, "FROM_ADDRESS");
        public final static Property Txid = new Property(8, String.class, "txid", false, "TXID");
    }


    public BuySellSellTodoDao(DaoConfig config) {
        super(config);
    }
    
    public BuySellSellTodoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BUY_SELL_SELL_TODO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ACCOUNT\" TEXT," + // 1: account
                "\"TOKEN\" TEXT," + // 2: token
                "\"ENTRUST_ORDER_ID\" TEXT," + // 3: entrustOrderId
                "\"USDT_AMOUNT\" TEXT," + // 4: usdtAmount
                "\"USDT_TO_ADDRESS\" TEXT," + // 5: usdtToAddress
                "\"QGAS_AMOUNT\" TEXT," + // 6: qgasAmount
                "\"FROM_ADDRESS\" TEXT," + // 7: fromAddress
                "\"TXID\" TEXT);"); // 8: txid
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BUY_SELL_SELL_TODO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BuySellSellTodo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(2, account);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(3, token);
        }
 
        String entrustOrderId = entity.getEntrustOrderId();
        if (entrustOrderId != null) {
            stmt.bindString(4, entrustOrderId);
        }
 
        String usdtAmount = entity.getUsdtAmount();
        if (usdtAmount != null) {
            stmt.bindString(5, usdtAmount);
        }
 
        String usdtToAddress = entity.getUsdtToAddress();
        if (usdtToAddress != null) {
            stmt.bindString(6, usdtToAddress);
        }
 
        String qgasAmount = entity.getQgasAmount();
        if (qgasAmount != null) {
            stmt.bindString(7, qgasAmount);
        }
 
        String fromAddress = entity.getFromAddress();
        if (fromAddress != null) {
            stmt.bindString(8, fromAddress);
        }
 
        String txid = entity.getTxid();
        if (txid != null) {
            stmt.bindString(9, txid);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BuySellSellTodo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(2, account);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(3, token);
        }
 
        String entrustOrderId = entity.getEntrustOrderId();
        if (entrustOrderId != null) {
            stmt.bindString(4, entrustOrderId);
        }
 
        String usdtAmount = entity.getUsdtAmount();
        if (usdtAmount != null) {
            stmt.bindString(5, usdtAmount);
        }
 
        String usdtToAddress = entity.getUsdtToAddress();
        if (usdtToAddress != null) {
            stmt.bindString(6, usdtToAddress);
        }
 
        String qgasAmount = entity.getQgasAmount();
        if (qgasAmount != null) {
            stmt.bindString(7, qgasAmount);
        }
 
        String fromAddress = entity.getFromAddress();
        if (fromAddress != null) {
            stmt.bindString(8, fromAddress);
        }
 
        String txid = entity.getTxid();
        if (txid != null) {
            stmt.bindString(9, txid);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BuySellSellTodo readEntity(Cursor cursor, int offset) {
        BuySellSellTodo entity = new BuySellSellTodo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // account
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // token
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // entrustOrderId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // usdtAmount
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // usdtToAddress
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // qgasAmount
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // fromAddress
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // txid
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BuySellSellTodo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAccount(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setToken(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEntrustOrderId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUsdtAmount(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUsdtToAddress(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setQgasAmount(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFromAddress(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTxid(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BuySellSellTodo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BuySellSellTodo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BuySellSellTodo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
