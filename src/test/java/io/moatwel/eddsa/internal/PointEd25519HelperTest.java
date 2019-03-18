package io.moatwel.eddsa.internal;

import io.moatwel.eddsa.internal.ed25519.PointEd25519Helper;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;

public class PointEd25519HelperTest {

    private static final PointInternal BASE = new PointInternal(
            new BigInteger("15112221349535400772501151409588531511454012693041857206046113283949847762202"),
            new BigInteger("46316835694926478169428394003475163141307993866256225615783033603165251855960")
    );

    @Test
    public void success_ScalarMultiple_1() {
        BigInteger seed = new BigInteger("1234");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("55556569241314067156494303609322045323771151550641480329783949256943018472903")));
        Assert.assertThat(point.getY(), is(new BigInteger("32784530584814531279135473125766128158866185447326682367874410721387968224179")));
    }

    @Test
    public void success_ScalarMultiple_2() {
        BigInteger seed = new BigInteger("35");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("57359645701093248420816504207718352998047016588956650167590481162962813908715")));
        Assert.assertThat(point.getY(), is(new BigInteger("838393992334862776911110796605108112109744556731718955387967463686536280019")));
    }

    @Test
    public void success_ScalarMultiple_3() {
        BigInteger seed = new BigInteger("42");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("42401546527934959338467043669041894126909913971352700899843678753185653162089")));
        Assert.assertThat(point.getY(), is(new BigInteger("29949868796287605534742663495443951076253075016671145616113943856256572857038")));
    }

    @Test
    public void success_ScalarMultiple_4() {
        BigInteger seed = new BigInteger("432");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("23185317896379073314204101893430457999697066311342414344062435044512680235749")));
        Assert.assertThat(point.getY(), is(new BigInteger("42847675453154813244888611769895755140794195615314248090347895993998306218966")));
    }

    @Test
    public void success_ScalarMultiple_5() {
        BigInteger seed = new BigInteger("4832");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("32920418660689032145069773849803705573656062787048336205043251221216966225190")));
        Assert.assertThat(point.getY(), is(new BigInteger("56026383279754517481079425070574944181225761936659225475464651151749539406243")));
    }

    @Test
    public void success_ScalarMultiple_6() {
        BigInteger seed = new BigInteger("24832");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("11631595154394978843377133379453165825237754642975162605149905885560815411567")));
        Assert.assertThat(point.getY(), is(new BigInteger("56190549384332382140433146152935211030343420853081847442949013909032210010540")));
    }

    @Test
    public void success_ScalarMultiple_7() {
        BigInteger seed = new BigInteger("724832");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("39792031458728826692492225466976114446949715078408820123383098037441723337016")));
        Assert.assertThat(point.getY(), is(new BigInteger("16142565858681328610009623699731614022500366825115680685420858027705801405463")));
    }

    @Test
    public void success_ScalarMultiple_8() {
        BigInteger seed = new BigInteger("3724832");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("37537294006832992264107349739659202882484087451165982252596111360744577176394")));
        Assert.assertThat(point.getY(), is(new BigInteger("44626458173786089741798966635832701770959815761801536704196433781061480576575")));
    }

    @Test
    public void success_ScalarMultiple_9() {
        BigInteger seed = new BigInteger("4024832");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("57565266020331081545170656735060038886618841117120379146746714107610578773517")));
        Assert.assertThat(point.getY(), is(new BigInteger("16504065882925451281818175768704195895857460814495240352462888679408028407735")));
    }

    @Test
    public void success_ScalarMultiple_10() {
        BigInteger seed = new BigInteger("4194303");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("49222229773374532393239607783181959645775503987541985433899891235920228553519")));
        Assert.assertThat(point.getY(), is(new BigInteger("50060870150529187365223388431884807734754073327434560837855405153975424635094")));
    }

    @Test
    public void success_ScalarMultiple_11() {
        BigInteger seed = new BigInteger("4194304");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("26993053454492735757774095526931594398941435313067218024451801426996192317244")));
        Assert.assertThat(point.getY(), is(new BigInteger("20756573878643363325793821026438350840612369149764266371668034898098235986297")));
    }

    @Test
    public void success_ScalarMultiple_12() {
        BigInteger seed = new BigInteger("20266806181347897178517736945403300566236311925948585575972021784256181966831");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("36568395279531091001405240627702774400329345357946000277861114291457062189012")));
        Assert.assertThat(point.getY(), is(new BigInteger("6892543919216139430465404745243127488161491607535545431263766463424432810420")));
    }

    @Test
    public void success_ScalarMultiple_13() {
        BigInteger seed = new BigInteger("11675954373387894284288004270057647646117187555908725144338394611307421402153");

        PointInternal point = PointEd25519Helper.scalarMultiply(BASE, seed);

        Assert.assertThat(point.getX(), is(new BigInteger("2550105584539864958223359997109982244652817874690374654323009420113342284222")));
        Assert.assertThat(point.getY(), is(new BigInteger("32100423734119761214020102691557112218747037854384677234614616607240732191696")));
    }
}
