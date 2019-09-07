package com.example.tuduzhao.jsoupblog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<JianShuBlog> blogList;
    private RecyclerView mRecyclerView;
    private BlogAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        connectJianShu();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
    }

    private void showBlog(List<JianShuBlog> blogs) {

        mAdapter = new BlogAdapter(this, blogList);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void connectJianShu() {
        blogList = new ArrayList<>();

        Observable.create(new ObservableOnSubscribe<List<JianShuBlog>>() {

            @Override
            public void subscribe(ObservableEmitter<List<JianShuBlog>> emitter) throws Exception {

                Document document = null;
                String url = "https://www.jianshu.com";
                document = Jsoup.connect(url).get();

                Elements input = document.getElementsByClass("have-img");
                Log.d("TTTT", "size:" + input.size());

                for (int i = 0; i < input.size(); i++) {

                    JianShuBlog blog = new JianShuBlog();

                    //获取id:.attr()方法通过key获取内容
                    String id = input.get(i).attr("data-note-id");

                    //获取图片链接:获取src属性的内容，只保留.png之前的内容，最前面拼接"https:"
                    String src = input.get(i).select("a").select("img").attr("src");
                    String s = src.substring(0, src.lastIndexOf("?"));
                    String img = "https:" + s;

                    //获取content元素
                    Elements content = input.get(i).getElementsByClass("content");

                    //获取标题和昵称：.text()直接获取标签的内容,由于div下有两个a标签，第一个是title，第二个是昵称
                    String title = content.select("a").get(0).text();
                    String name = content.select("a").get(1).text();

                    //获取链接：.attr()通过key获取内容
                    String href = "https://www.jianshu.com/" + content.select("a").get(0).attr("href");
                    //获取摘要：与获取标题一样的方法
                    String abs = content.select("p").text();
                    //获取价值和喜欢：价值和喜欢数量在div标签的两个span标签，因此，第0个是价值，第1个是喜欢数量
                    String paid = content.select("div").select("span").get(0).text();
                    String like = content.select("div").select("span").get(1).text();

                    blog.setId(id);
                    blog.setHref(href);
                    blog.setImg(img);
                    blog.setAbs(abs);
                    blog.setLike(like);
                    blog.setName(name);
                    blog.setPaid(paid);
                    blog.setTitle(title);

                    blogList.add(blog);
                    emitter.onNext(blogList);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<JianShuBlog>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<JianShuBlog> blogs) {
                        showBlog(blogs);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
