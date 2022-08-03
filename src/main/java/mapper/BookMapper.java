package mapper;

import com.github.pagehelper.Page;
import domain.Book;
import org.apache.ibatis.annotations.*;

/**
 * 图书接口
 */
public interface BookMapper {

    /**
     * 按上架时间倒序查询出未下架的图书信息
     * @return 查询结果page
     */
    @Select("select * from book where book_status != '3' order by book_uploadtime desc")
    @Results(id = "bookMap" , value ={
            //id字段默认为false，表示不是主键
            //column表示数据库字段，property表示实体类属性名。
            @Result(id = true ,column = "book_id" , property = "id"),
            @Result(column = "book_name" , property = "name"),
            @Result(column = "book_isbn" , property = "isbn"),
            @Result(column = "book_press" , property = "press"),
            @Result(column = "book_author",property = "author"),
            @Result(column = "book_pagination",property = "pagination"),
            @Result(column = "book_price",property = "price"),
            @Result(column = "book_uploadtime",property = "uploadTime"),
            @Result(column = "book_status",property = "status"),
            @Result(column = "book_borrower",property = "borrower"),
            @Result(column = "book_borrowtime",property = "borrowTime"),
            @Result(column = "book_returntime",property = "returnTime")
    })
    Page<Book> selectNewBooks();

    /**
     * 根据id查询图书信息
     */
    @Select("select * from book where book_id=#{id}")
    @ResultMap("bookMap")
    Book findBookById(String id);

    /**
     * 编辑图书信息
     */
    Integer editBook(Book book);

    /**
     * 分页查询图书
     * @param book 查询图书的条件
     * @return 分页图书信息
     */
    @Select({
            "<script>" +
                    "select * from book " +
                    "where book_status != '3'" +
                    "<if test=\"name != null\"> and book_name like concat('%',#{name},'%')</if>" +
                    "<if test=\"press != null\"> and book_press like concat('%',#{press},'%')</if>" +
                    "<if test=\"author != null\"> and book_author like concat('%',#{author},'%')</if>" +
                    "order by book_status" +
            "</script>"
    })
    @ResultMap("bookMap")
    Page<Book> searchBooks(Book book);

    /**
     * 新增图书
     * @param book 新增的图书信息
     * @return 操作成功数
     */
    Integer addBook(Book book);

    /**
     * 查询借阅但未归还的图书和所有待确认归还的图书
     * @param book 图书信息
     * @return
     */
    @Select({
            "<script>" +
                    "select * from book " +
                    "where book_borrower = #{borrower}" +
                    "and book_status in('1','2')" +
                    "<if test=\"name != null\"> and book_name like concat('%',#{name},'%')</if>" +
                    "<if test=\"press != null\"> and book_press like concat('%',#{press},'%')</if>" +
                    "<if test=\"author != null\"> and book_author like concat('%',#{author},'%')</if>" +
                    "order by book_borrowtime" +
            "</script>"

    })
    @ResultMap("bookMap")
    Page<Book> selectBorrowed(Book book);

    /**
     * 查询借阅但未归还的图书
     * @param book 图书信息
     * @return
     */
    @Select({
            "<script>" +
                    "select * from book " +
                    "where book_borrower = #{borrower}" +
                    "and book_status = '1'" +
                    "<if test=\"name != null\"> and book_name like concat('%',#{name},'%')</if>" +
                    "<if test=\"press != null\"> and book_press like concat('%',#{press},'%')</if>" +
                    "<if test=\"author != null\"> and book_author like concat('%',#{author},'%')</if>" +
                    "order by book_borrowtime" +
            "</script>"

    })
    @ResultMap("bookMap")
    Page<Book> selectMyBorrowed(Book book);
}
