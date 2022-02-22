package com.yjy.moxibustion;

import com.yjy.moxibustion.dao.*;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.*;
import com.yjy.moxibustion.service.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.formula.functions.Rows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = MoxibustionApplication.class)
//@Transactional
//@Rollback
class MoxibustionApplicationTests {

    @Autowired
    private PositionCategoryMapper positionCategoryMapper;

    @Autowired
    private PositionContentMapper positionContentMapper;

    @Autowired
    private BranchCategoryMapper branchCategoryMapper;

    @Autowired
    private BranchContentMapper branchContentMapper;

    @Autowired
    private XueweiCategoryMapper xueweiCategoryMapper;

    @Autowired
    private XueweiContentMapper xueweiContentMapper;

    @Autowired
    private DiseaseByBranchCategoryService diseaseByBranchCategoryService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanFeedbackService planFeedbackService;

    @Autowired
    private PlanFeedbackMapper planFeedbackMapper;

    @Autowired
    private DiseaseByPositionCategoryService diseaseByPositionCategoryService;

    @Test
    void testSelect() {
        List<PositionCategory> positionCategories = positionCategoryMapper.selectAll();
        for (PositionCategory positionCategory : positionCategories) {
            System.out.println(positionCategory.getName());
        }
    }

    @Test
    void saveAllPositionCategory(){
        String path = "src/main/resources/excel/按部位数据表.xlsx";
        List<PositionCategory> allByPositionExcel = getAllByPositionExcel(path);
        for (PositionCategory positionCategory : allByPositionExcel) {
            System.out.println(positionCategory);
        }
        positionCategoryMapper.insertList(allByPositionExcel);


    }

    //读取Excel中的数据，放到list中返回
    private List<PositionCategory> getAllByPositionExcel(String path){
        List<PositionCategory> list = new ArrayList<PositionCategory>();
        List<PositionCategory> list1 = positionCategoryMapper.selectAll();
        Integer id = 13;
        try {
            InputStream inputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            //获得行数
            int rows = sheetAt.getLastRowNum();
            //跳过行首
            for (int i = 1; i <= rows; i++) {
                //取得第i行的数据
                XSSFRow row = sheetAt.getRow(i);
                PositionCategory positionCategory = new PositionCategory();
                //一级指标
                String one = row.getCell(0).getStringCellValue();
                //二级指标
                String two = row.getCell(1).getStringCellValue();
                for (PositionCategory category : list1) {
                    //寻找当前内容的一级指标参数
                    if (category.getName().equals(one)){
                        positionCategory.setId(id);
                        positionCategory.setParentId(category.getId());
                        positionCategory.setName(two);
                        positionCategory.setStatus(1);
                        positionCategory.setSortOrder(1);
                        positionCategory.setIsParent(false);
                        positionCategory.setCreated(LocalDateTime.now());
                        positionCategory.setUpdated(LocalDateTime.now());
                        break;
                    }
                }
                list.add(positionCategory);
                id++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Test
    void saveAllPositionContent(){
        String path = "src/main/resources/excel/按部位数据表.xlsx";
        List<PositionContent> allPositionContentByExcel = getAllPositionContentByExcel(path);
        positionContentMapper.insertList(allPositionContentByExcel);
    }

    //读取Excel，返回按部位分类的具体内容
    private List<PositionContent> getAllPositionContentByExcel(String path){
        List<PositionContent> list = new ArrayList<PositionContent>();
        //用于存放类别(即该内容属于哪个类别)
        List<PositionCategory> list1 = positionCategoryMapper.selectAll();
        try {
            InputStream inputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            int rows = sheetAt.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                XSSFRow row = sheetAt.getRow(i);
                PositionContent content = new PositionContent();
                for (PositionCategory positionCategory : list1) {
                    //找到所属类别的id
                    if (row.getCell(1).getStringCellValue().equals(positionCategory.getName())){
                        content.setCategoryId(positionCategory.getId());
                        content.setGaishu(row.getCell(2).getStringCellValue());
                        content.setZhengzhuang(row.getCell(3).getStringCellValue());
                        XSSFCell cell1 = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (cell1!=null){
                            content.setBingyin(row.getCell(4).getStringCellValue());
                        }
                        content.setAijiuquxue(row.getCell(5).getStringCellValue());
                        XSSFCell cell2 = row.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (cell2!=null){
                            content.setAnli(row.getCell(6).getStringCellValue());
                        }
                        XSSFCell cell3 = row.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (cell3!=null){
                            content.setTihui(row.getCell(7).getStringCellValue());
                        }
                        content.setCreated(LocalDateTime.now());
                        content.setUpdated(LocalDateTime.now());
                        break;
                    }
                }
                list.add(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Test
    void saveAllBranchCategory(){
        String path = "src/main/resources/excel/按分科.xlsx";
        List<BranchCategory> allBranchCategoryByExcel = getAllBranchCategoryByExcel(path);
        /*for (BranchCategory category : allBranchCategoryByExcel) {
            System.out.println(category);
        }*/
        branchCategoryMapper.insertList(allBranchCategoryByExcel);
    }

    //从Excel中获取按分科的类别
    private List<BranchCategory> getAllBranchCategoryByExcel(String path){
        List<BranchCategory> list = new ArrayList<BranchCategory>();
        List<BranchCategory> list1 = branchCategoryMapper.selectAll();
        try {
            InputStream inputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            int rows = sheetAt.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                XSSFRow row = sheetAt.getRow(i);
                BranchCategory category = new BranchCategory();
                //若三级指标不为空
                if (row.getCell(1,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                    for (BranchCategory branchCategory : list1) {
                        if (row.getCell(0).getStringCellValue().equals(branchCategory.getName())){
                            category.setParentId(branchCategory.getId());
                            category.setName(row.getCell(1).getStringCellValue());
                            category.setStatus(1);
                            category.setSortOrder(1);
                            category.setIsParent(false);
                            category.setCreated(LocalDateTime.now());
                            category.setUpdated(LocalDateTime.now());
                            break;
                        }
                    }
                }else {
                    continue;
                }
                list.add(category);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Test
    void saveBranchContent(){
        String path = "src/main/resources/excel/按分科.xlsx";
        List<BranchContent> allBranchContentByExcel = getAllBranchContentByExcel(path);
        /*for (BranchContent content : allBranchContentByExcel) {
            System.out.println(content.getCategoryId());
        }*/
        branchContentMapper.insertList(allBranchContentByExcel);
    }

    private List<BranchContent> getAllBranchContentByExcel(String path){
        List<BranchContent> list = new ArrayList<BranchContent>();
        List<BranchCategory> list1 = branchCategoryMapper.selectAll();
        try {
            InputStream inputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            int rows = sheetAt.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                XSSFRow row = sheetAt.getRow(i);
                BranchContent content = new BranchContent();
                //如果三级指标不为空
                if (row.getCell(1,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                    for (BranchCategory category : list1) {
                        if (category.getName().equals(row.getCell(1).getStringCellValue())){
                            content.setCategoryId(category.getId());
                            content.setGaishu(row.getCell(2).getStringCellValue());
                            if (row.getCell(3,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setZhengzhuang(row.getCell(3).getStringCellValue());
                            }
                            if (row.getCell(4,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setBingyin(row.getCell(4).getStringCellValue());
                            }
                            if (row.getCell(5,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setAnli(row.getCell(5).getStringCellValue());
                            }
                            if (row.getCell(6,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setTihui(row.getCell(6).getStringCellValue());
                            }
                            content.setAijiuquxue(row.getCell(7).getStringCellValue());
                            content.setCreated(LocalDateTime.now());
                            content.setUpdated(LocalDateTime.now());
                        }
                    }
                }else {
                    //三级指标为空，二级指标为其父节点
                    for (BranchCategory category : list1) {
                        if (row.getCell(0).getStringCellValue().equals(category.getName())){
                            content.setCategoryId(category.getId());
                            content.setGaishu(row.getCell(2).getStringCellValue());
                            if (row.getCell(3,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setZhengzhuang(row.getCell(3).getStringCellValue());
                            }
                            if (row.getCell(4,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setBingyin(row.getCell(4).getStringCellValue());
                            }
                            if (row.getCell(5,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setAnli(row.getCell(5).getStringCellValue());
                            }
                            if (row.getCell(6,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                                content.setTihui(row.getCell(6).getStringCellValue());
                            }
                            content.setAijiuquxue(row.getCell(7).getStringCellValue());
                            content.setCreated(LocalDateTime.now());
                            content.setUpdated(LocalDateTime.now());
                        }
                    }
                }
                list.add(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Test
    void saveAllXueweiCategory(){
        String path = "src/main/resources/excel/穴位查询.xlsx";
        List<XueweiCategory> allXueweiCategoryByExcel = getAllXueweiCategoryByExcel(path);
        /*for (XueweiCategory xueweiCategory : allXueweiCategoryByExcel) {
            System.out.println(xueweiCategory);
        }*/
        xueweiCategoryMapper.insertList(allXueweiCategoryByExcel);
    }

    private List<XueweiCategory> getAllXueweiCategoryByExcel(String path){
        List<XueweiCategory> list = new ArrayList<XueweiCategory>();
        List<XueweiCategory> list1 = xueweiCategoryMapper.selectAll();
        try {
            InputStream inputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            int rows = sheetAt.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                XSSFRow row = sheetAt.getRow(i);
                XueweiCategory xueweiCategory = new XueweiCategory();
                for (XueweiCategory category : list1) {
                    if (row.getCell(0).getStringCellValue().equals(category.getName())){
                        xueweiCategory.setParentId(category.getId());
                        xueweiCategory.setName(row.getCell(1).getStringCellValue());
                        xueweiCategory.setStatus(1);
                        xueweiCategory.setSortOrder(1);
                        xueweiCategory.setIsParent(false);
                        xueweiCategory.setCreated(LocalDateTime.now());
                        xueweiCategory.setUpdated(LocalDateTime.now());
                        break;
                    }
                }
                list.add(xueweiCategory);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Test
    void saveXueweiContent(){
        String path = "src/main/resources/excel/穴位查询.xlsx";
        List<XueweiContent> allXueweiContentByExcel = getAllXueweiContentByExcel(path);
        /*for (XueweiContent xueweiContent : allXueweiContentByExcel) {
            System.out.println(xueweiContent);
        }*/
        xueweiContentMapper.insertList(allXueweiContentByExcel);
    }

    private List<XueweiContent> getAllXueweiContentByExcel(String path){
        List<XueweiContent> list = new ArrayList<XueweiContent>();
        List<XueweiCategory> list1 = xueweiCategoryMapper.selectAll();
        try {
            InputStream inputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            int rows = sheetAt.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                XSSFRow row = sheetAt.getRow(i);
                XueweiContent xueweiContent = new XueweiContent();
                for (XueweiCategory category : list1) {
                    if (row.getCell(1).getStringCellValue().equals(category.getName())){
                        xueweiContent.setCategoryId(category.getId());
                        xueweiContent.setGuojidaima(row.getCell(3).getStringCellValue());
                        if (row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)!=null){
                            xueweiContent.setTedingxue(row.getCell(4).getStringCellValue());
                        }
                        xueweiContent.setDingwei(row.getCell(5).getStringCellValue());
                        xueweiContent.setQuxuefa(row.getCell(6).getStringCellValue());
                        xueweiContent.setZhuzhi(row.getCell(7).getStringCellValue());
                        xueweiContent.setCanshu(row.getCell(8).getStringCellValue());
                        xueweiContent.setYingyong(row.getCell(9).getStringCellValue());
                        //照片目前为空
                        xueweiContent.setCreated(LocalDateTime.now());
                        xueweiContent.setUpdated(LocalDateTime.now());
                        break;
                    }
                }
                list.add(xueweiContent);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Test
    void testTkMybatisExample(){
        Integer id = diseaseByBranchCategoryService.getBranchCategoryByName("小儿发黄");
        System.out.println(id);
    }

    @Test
    void testTree(){
        List<BranchCategory> targetList = new ArrayList<>();
        List<BranchCategory> sourceList = branchCategoryMapper.selectAll();

        sortList(sourceList,targetList,0);
        for (BranchCategory category : targetList) {
            System.out.println(category);
        }
    }

    private void sortList(List<BranchCategory> sourceList,List<BranchCategory> targetList,int parentId){
        for (BranchCategory source : sourceList) {
            if (source.getParentId().equals(parentId)){
                targetList.add(source);

                //判断有没有子节点，有则继续追加
                if (source.getIsParent()){
                    for (BranchCategory current : sourceList) {
                        if (current.getParentId().equals(source.getId())){
                            sortList(sourceList,targetList,source.getId());
                            break;
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testGetTree(){
        List<Object> tree = diseaseByBranchCategoryService.getTree();
        for (Object o : tree) {
            System.out.println(o);
        }
    }

    @Test
    void testUpdate(){
        int i = diseaseByBranchCategoryService.updateName(64, "牙痛啊");
        System.out.println(i);
    }

    @Test
    void testDeleteByCid(){
        int i = diseaseByBranchCategoryService.deleteById(66);
        System.out.println(i);
    }

    @Test
    void testAddBranchCategory(){
        int add = diseaseByBranchCategoryService.add(67);
        System.out.println(add);
        //返回1，代表添加成功
    }

    @Test
    void testLogin(){
        Admin admin = new Admin();
        admin.setUsername("yjy");
        String pwd = "123456";
        admin.setPassword(DigestUtils.md5DigestAsHex(pwd.getBytes()));
        admin.setPhone("15366666666");
        admin.setEmail("yjy@qq.com");
        admin.setCreated(LocalDateTime.now());
        admin.setUpdated(LocalDateTime.now());
        adminMapper.insert(admin);
    }

    @Test
    void testUserConnect(){
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试治疗方案反馈
     */
    @Test
    void testPlanFeedbackFindAllByType(){
        List<PlanFeedback> allByType = planFeedbackService.findAllByType(1);
        for (PlanFeedback planFeedback : allByType) {
            System.out.println(planFeedback);
        }
        /*PlanFeedback planFeedback  = new PlanFeedback();
        System.out.println(planFeedback);*/
    }

    @Test
    void testPlanFeedbackDeleteById(){
        PlanFeedback planFeedback = new PlanFeedback(2,1,334,"test can insert?",LocalDateTime.now(),LocalDateTime.now());
        planFeedbackService.save(planFeedback);
        List<PlanFeedback> planFeedbacks = planFeedbackMapper.selectAll();
        for (PlanFeedback feedback : planFeedbacks) {
            System.out.println(feedback);
        }

//        int i = planFeedbackService.deleteById(1);
//        System.out.println(i);
    }

    @Test
    void testUserExistByName(){
        String name = "yjynnn";
        //返回名字为name的个数，返回0代表没有该用户
        int i = userService.userExistByName(name);
        System.out.println(i);
    }

    @Test
    void testDiseaseByPositionCategoryService(){
        List<Map<String, Object>> list = diseaseByPositionCategoryService.positionUserSearchByName("头部");
        for (Map<String, Object> map : list) {
            System.out.println(map.toString());
        }
    }
}
