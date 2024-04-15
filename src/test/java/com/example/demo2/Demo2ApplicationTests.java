package com.example.demo2;

import com.example.demo2.entity.Account;
import com.example.demo2.mapper.AccountMapper;
import com.example.demo2.vo.AccountVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.example.demo2.entity.table.AccountTableDef.ACCOUNT;

@SpringBootTest
class Demo2ApplicationTests {
    @Autowired
    private AccountMapper accountMapper;
    @Test
    void contextLoads() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .where(ACCOUNT.ID.eq(2));
        Account account = accountMapper.selectOneByQuery(queryWrapper);
        System.out.println(account);
    }
    @Test
    void testInsert(){
        //不带主键插入
        Account account=new Account();
        account.setUserName("王五");
        account.setAge(20);
        account.setBirthday(new Date());
        int rows;
        rows=accountMapper.insert(account);
        System.out.println("AccountId:"+account.getId());
        Assertions.assertEquals(1,rows);
    }
    @Test
    void testInsertWithPk() {
        // gender 0 女 1 男 3 未知 默认 3
        Account account = new Account();
        account.setId(19L);
        account.setUserName("tester3");
        account.setAge(18);
        account.setBirthday(new Date());
        // 带主键插入
        int rows;
//        rows = accountMapper.insertWithPk(account);
        rows = accountMapper.insertSelectiveWithPk(account);
//        rows = accountMapper.insertWithPk(account, true);
        Assertions.assertEquals(1, rows);
    }
    @Test
    void testInsertOrUpdate(){
        //插入更新
        Account account=new Account();
        account.setId(3L);
        account.setUserName("蟹老板");
        account.setAge(18);
        int rows;
        rows=accountMapper.insertOrUpdateSelective(account);
        Assertions.assertEquals(1,rows);
    }
    @Test
    void testInsertBatch() {
        // 批量更新
        ArrayList<Account> arrayList = new ArrayList<>();
        // insert into tb_account (user_name, age) values ('batch1', 18) ...
        arrayList.add(Account.builder()
                .userName("batch1")
                .age(18)
                .build());
        arrayList.add(Account.builder()
                .userName("batch3")
                .age(20)
                .birthday(new Date())
                .build());
        arrayList.add(Account.builder()
                .userName("batch2")
                .age(19)
                .build());
        int rows = accountMapper.insertBatch(arrayList);
        // 不忽略 null 值 Db
        Assertions.assertEquals(3, rows);
    }
    @Test
    void testDelete(){
        //条件删除
        int rows=0;
        rows=accountMapper.deleteById(3);
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("id",4);
        rows +=accountMapper.deleteByMap(hashMap);
        rows +=accountMapper.deleteByCondition(ACCOUNT.ID.eq(5));
        rows +=accountMapper.deleteByQuery(QueryWrapper.create().where(ACCOUNT.ID.eq(6)));
        Assertions.assertEquals(4,rows);
    }
    @Test
    void testDeleteBatch() {
        // 批量删除

        int rows = accountMapper.deleteBatchByIds(Arrays.asList(7, 8, 9));

        Assertions.assertEquals(3, rows);
    }

    @Test
    void testFullDelete() {
        // 全表删除

        Assertions.assertThrows(Exception.class, () -> {
            accountMapper.deleteByQuery(QueryWrapper.create());
        });
    }
    @Test
    void testUpdate() {
        Account account = new Account();
        account.setId(1L);
        account.setAge(100);

        // 根据 ID 更新

        accountMapper.update(account);
//        accountMapper.update(account, false);
    }

    @Test
    void testUpdateCondition() {
        Account account = new Account();
        account.setAge(200);

        // 根据条件更新

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_name", "张三");
        accountMapper.updateByMap(account, hashMap);
        accountMapper.updateByCondition(account, ACCOUNT.USER_NAME.eq("张三"));
        accountMapper.updateByQuery(account,
                QueryWrapper.create().where(ACCOUNT.USER_NAME.eq("张三")));
    }

    @Test
    void testUpdateNumber() {
        // 数字字段 +n set age = age + n

        //accountMapper.updateNumberAddByQuery(ACCOUNT.AGE, 1,
          //      QueryWrapper.create().where(ACCOUNT.USER_NAME.eq("张三")));
    }

    @Test
    void testFullUpdate() {
        Account account = new Account();
        account.setAge(100);

        // 全表更新

        Assertions.assertThrows(Exception.class, () -> {
            accountMapper.updateByQuery(account, QueryWrapper.create());
        });
    }
    @Test
    void testUpdateEntity() {
        Account account = UpdateEntity.of(Account.class, 1);
        account.setId(1L);
        account.setBirthday(null);
        account.setAge(18);

        // gender -> null
        // user_name -> null

        // set birthday = null, age = 18

        // set age = 18
        // set birthday = null, age = 18, gender = null, user_name = null

        int rows = accountMapper.update(account);

        Assertions.assertEquals(1, rows);
    }

    @Test
    void testUpdateWrapper() {
        Account account = UpdateEntity.of(Account.class, 1);

        UpdateWrapper updateWrapper = (UpdateWrapper) account;

        // update set user_name = 'zhangsan', age = age + 1

        updateWrapper.set(ACCOUNT.USER_NAME, "zhangsan");
        updateWrapper.set(ACCOUNT.AGE, ACCOUNT.AGE.add(1));

        int rows = accountMapper.update(account);

        Assertions.assertEquals(1, rows);
    }
    @Test
    void testSelectOne() {
        Account account;

       //account = accountMapper.selectOneById(1);
        //HashMap<String, Object> hashMap = new HashMap<>();
       //hashMap.put("id", 1);
       // account = accountMapper.selectOneByMap(hashMap);
        account = accountMapper.selectOneByCondition(ACCOUNT.ID.ge(1));
//        account = accountMapper.selectOneByQuery(
//                QueryWrapper.create()
//                        .where(ACCOUNT.ID.ge(1))
//        );

        System.out.println(account);
    }

    @Test
    void testSelectList() {
        List<Account> accounts = accountMapper.selectListByQuery(
                QueryWrapper.create()
                        .select(ACCOUNT.USER_NAME, ACCOUNT.AGE)
                        .where(ACCOUNT.AGE.ge(18))
        );

        accounts.forEach(System.out::println);
    }

    @Test
    void testSelectObject() {
        /*Object object = accountMapper.selectObjectByQuery(
                QueryWrapper.create()
                        .select(ACCOUNT.AGE, ACCOUNT.USER_NAME)
                        .where(ACCOUNT.ID.eq(1))
        );

        System.out.println(object);*/

        List<Object> objects = accountMapper.selectObjectListByQuery(
                QueryWrapper.create()
                        .select(ACCOUNT.USER_NAME)
                        .where(ACCOUNT.AGE.ge(18))
        );

        System.out.println(objects);
    }

    @Test
    void testSelectMap() {
        List<Row> rows = accountMapper.selectRowsByQuery(
                QueryWrapper.create()
                        .where(ACCOUNT.ID.eq(1))
        );

        Row row = rows.get(0);

        System.out.println(row);
        System.out.println(row.getInt("id"));
    }
    @Test
    void testSelectAs() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(/*ACCOUNT.ALL_COLUMNS,*/
                        /*if_(ACCOUNT.AGE.ge(18), true_(), false_()).as("is_adult")*/
                        ACCOUNT.USER_NAME)
                .where(ACCOUNT.ID.ge(1));

        AccountVO accountVO = accountMapper.selectOneByQueryAs(queryWrapper, AccountVO.class);
        System.out.println(accountVO);

        List<AccountVO> accountVOS = accountMapper.selectListByQueryAs(queryWrapper, AccountVO.class);
        accountVOS.forEach(System.out::println);

        Integer integer = accountMapper.selectObjectByQueryAs(queryWrapper, Integer.class);
        System.out.println(integer);

        List<String> integers = accountMapper.selectObjectListByQueryAs(queryWrapper, String.class);
        System.out.println(integers);
    }
}
