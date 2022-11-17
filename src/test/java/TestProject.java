import org.junit.Assert;
import shorturlanalyzer.Analyzer;
import shorturlanalyzer.UrlInfo;

@SuppressWarnings("HttpUrlsUsage")
public class TestProject {

    @org.junit.Test
    public void test() {
        String url1 = "https://bit.ly/28M1lbJ";          //bit.ly
        String url2 = "http://tinyurl.com/yxnc3gr8";    //tinyurl.com
        String url3 = "http://tiny.cc/0711vz";          //tiny.cc
        String url4 = "http://ow.ly/d9AV30jRJWJ";       //ow.ly
        String url5 = "http://w.wiki/kSf";              //w.wiki
        String url6;
        String url7;
        String url8;
        String url9;
        String url10;

        String url11 = "http://maven.apache.org/";                      // no redirecting
        String url12 = "https://maven.apache.org/what-is-mave.html";    // 404 - not found
        String url13 = "http://127.0.0.1:65500/index.html";             // link not reachable

        Analyzer analyzer1 = new Analyzer(url1);
        UrlInfo info1 = analyzer1.getAllInfo();
        Assert.assertTrue(info1.isReachable());
        Assert.assertTrue(info1.hasRedirecting());
        Assert.assertEquals("URL " + info1.getOriginalUrl() + " --> " + info1.getFinalUrl(),
                "https://en.wikipedia.org/wiki/Internet_security", info1.getFinalUrl());

        Analyzer analyzer2 = new Analyzer(url2);
        UrlInfo info2 = analyzer2.getAllInfo();
        Assert.assertTrue(info2.isReachable());
        Assert.assertTrue(info2.hasRedirecting());
        Assert.assertEquals("URL " + info2.getOriginalUrl() + " --> " + info2.getFinalUrl(),
                "https://helpdeskgeek.com/", info2.getFinalUrl());

        Analyzer analyzer3 = new Analyzer(url3);
        UrlInfo info3 = analyzer3.getAllInfo();
        Assert.assertTrue(info3.isReachable());
        Assert.assertTrue(info3.hasRedirecting());
        Assert.assertEquals("URL " + info3.getOriginalUrl() + " --> " + info3.getFinalUrl(),
                "https://stackoverflow.com/", info3.getFinalUrl());

        Analyzer analyzer4 = new Analyzer(url4);
        UrlInfo info4 = analyzer4.getAllInfo();
        Assert.assertTrue(info4.isReachable());
        Assert.assertTrue(info4.hasRedirecting());
        Assert.assertEquals("URL " + info4.getOriginalUrl() + " --> " + info4.getFinalUrl(),
                "https://blog.leadquizzes.com/ubersuggest-find-profitable-keywords-with-neil-patels-free-seo-tool?platform=hootsuite",
                info4.getFinalUrl());

        Analyzer analyzer5 = new Analyzer(url5);
        UrlInfo info5 = analyzer5.getAllInfo();
        Assert.assertTrue(info5.isReachable());
        Assert.assertTrue(info5.hasRedirecting());
        Assert.assertEquals("URL " + info5.getOriginalUrl() + " --> " + info5.getFinalUrl(),
                "https://it.wikipedia.org/wiki/Aiuto:Aiuto", info5.getFinalUrl());

        Analyzer analyzer11 = new Analyzer(url11);
        UrlInfo info11 = analyzer11.getAllInfo();
        Assert.assertTrue(info11.isReachable());
        Assert.assertTrue(info11.isPageFound());
        Assert.assertFalse(info11.hasRedirecting());

        Analyzer analyzer12 = new Analyzer(url12);
        UrlInfo info12 = analyzer12.getAllInfo();
        Assert.assertTrue(info12.isReachable());
        Assert.assertFalse(info12.isPageFound());

        Analyzer analyzer13 = new Analyzer(url13);
        UrlInfo info13 = analyzer13.getAllInfo();
        Assert.assertFalse(info13.isReachable());
    }

}
