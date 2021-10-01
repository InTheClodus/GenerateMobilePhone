package com.lf.generatemobilephone

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Contacts
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lf.generatemobilephone.adapter.NumberAdapter
import com.lf.generatemobilephone.view.MyGridView
import java.io.UnsupportedEncodingException
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var values: ContentValues? = null
    var textView: TextView? = null
    private var number2 = ""
    var numbers: List<Number>? = null
    var progressDialog: ProgressDialog? = null
    var numberAdapter: NumberAdapter? = null
    private val WRITE_PERMISSION_REQUEST = 0
    private var generateNum: Int = 100
    var areaCodeEdit: EditText? = null
    var areaCodeBtn: Button? = null
    var areaCodeTxt: TextView? = null

    var generateNumEdit: EditText? = null
    var generateNumBtn: Button? = null
    var generateNumTxt: TextView? = null

    var yidong = arrayOf(
        "134", "135", "136", "137", "138", "139", "147",
        "150", "151", "152", "154", "157", "158", "159",
        "178", "182", "183", "184", "187", "188"
    )
    var liantong = arrayOf(
        "130", "131", "132", "145", "155", "156", "176", "185", "186"
    )
    var dianxin = arrayOf("133", "153", "177", "180", "181", "189")


    /**
     *  598 百家姓
     *  来源-CSDN博客 --https://blog.csdn.net/zzy2011266/article/details/42011047
     *  */
    var Surname = arrayOf(
        "赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许",
            "何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎",
            "鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤","滕","殷",
            "罗","毕","郝","邬","安","常","乐","于","时","傅","皮","卞","齐","康","伍","余","元","卜","顾","孟","平","黄","和",
            "穆","萧","尹","姚","邵","湛","汪","祁","毛","禹","狄","米","贝","明","臧","计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒",
            "屈","项","祝","董","梁","杜","阮","蓝","闵","席","季","麻","强","贾","路","娄","危","江","童","颜","郭","梅","盛","林","刁","钟",
            "徐","邱","骆","高","夏","蔡","田","樊","胡","凌","霍","虞","万","支","柯","昝","管","卢","莫","经","房","裘","缪","干","解","应",
            "宗","丁","宣","贲","邓","郁","单","杭","洪","包","诸","左","石","崔","吉","钮","龚","程","嵇","邢","滑","裴","陆","荣","翁","荀",
            "羊","于","惠","甄","曲","家","封","芮","羿","储","靳","汲","邴","糜","松","井","段","富","巫","乌","焦","巴","弓","牧","隗","山",
            "谷","车","侯","宓","蓬","全","郗","班","仰","秋","仲","伊","宫","宁","仇","栾","暴","甘","钭","厉","戎","祖","武","符","刘","景",
            "詹","束","龙","叶","幸","司","韶","郜","黎","蓟","溥","印","宿","白","怀","蒲","邰","从","鄂","索","咸","籍","赖","卓","蔺","屠",
            "蒙","池","乔","阴","郁","胥","能","苍","双","闻","莘","党","翟","谭","贡","劳","逄","姬","申","扶","堵","冉","宰","郦","雍","却",
            "璩","桑","桂","濮","牛","寿","通","边","扈","燕","冀","浦","尚","农","温","别","庄","晏","柴","瞿","阎","充","慕","连","茹","习",
            "宦","艾","鱼","容","向","古","易","慎","戈","廖","庾","终","暨","居","衡","步","都","耿","满","弘","匡","国","文","寇","广","禄",
            "阙","东","欧","殳","沃","利","蔚","越","夔","隆","师","巩","厍","聂","晁","勾","敖","融","冷","訾","辛","阚","那","简","饶","空",
            "曾","毋","沙","乜","养","鞠","须","丰","巢","关","蒯","相","查","后","荆","红","游","郏","竺","权","逯","盖","益","桓","公","仉",
            "督","岳","帅","缑","亢","况","郈","有","琴","归","海","晋","楚","闫","法","汝","鄢","涂","钦","商","牟","佘","佴","伯","赏","墨",
            "哈","谯","篁","年","爱","阳","佟","言","福","南","火","铁","迟","漆","官","冼","真","展","繁","檀","祭","密","敬","揭","舜","楼",
            "疏","冒","浑","挚","胶","随","高","皋","原","种","练","弥","仓","眭","蹇","覃","阿","门","恽","来","綦","召","仪","风","介","巨",
            "木","京","狐","郇","虎","枚","抗","达","杞","苌","折","麦","庆","过","竹","端","鲜","皇","亓","老","是","秘","畅","邝","还","宾",
            "闾","辜","纵","侴","万俟","司马","上官","欧阳","夏侯","诸葛","闻人","东方","赫连","皇甫","羊舌","尉迟","公羊","澹台","公冶","宗正",
            "濮阳","淳于","单于","太叔","申屠","公孙","仲孙","轩辕","令狐","钟离","宇文","长孙","慕容","鲜于","闾丘","司徒","司空","兀官","司寇",
            "南门","呼延","子车","颛孙","端木","巫马","公西","漆雕","车正","壤驷","公良","拓跋","夹谷","宰父","谷梁","段干","百里","东郭","微生",
            "梁丘","左丘","东门","西门","南宫","第五","公仪","公乘","太史","仲长","叔孙","屈突","尔朱","东乡","相里","胡母","司城","张廖","雍门",
            "毋丘","贺兰","綦毋","屋庐","独孤","南郭","北宫","王孙"
    );


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initWidget()
        getWrieteConstactsPermission()
        requestReadExternalPermission()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        numbers = ArrayList<Number>()
        val ydGridView = findViewById<View>(R.id.yidong) as MyGridView
        numberAdapter = NumberAdapter(this, yidong)
        ydGridView.adapter = numberAdapter
        val ldGridView = findViewById<View>(R.id.liantong) as MyGridView
        numberAdapter = NumberAdapter(this, liantong)
        ldGridView.adapter = numberAdapter
        val dxGridView = findViewById<View>(R.id.dianxin) as MyGridView
        numberAdapter = NumberAdapter(this, dianxin)
        dxGridView.adapter = numberAdapter
    }

    /**
     * 初始化控件
     */
    private fun initWidget(){
        areaCodeEdit = findViewById(R.id.areaCodeEdit)
        areaCodeBtn = findViewById(R.id.areaCodeBtn)
        areaCodeTxt = findViewById(R.id.areaCodeTxt)

        generateNumEdit = findViewById(R.id.generateNumEdit)
        generateNumBtn = findViewById(R.id.generateNumBtn)
        generateNumTxt = findViewById(R.id.generateNumTxt)

    }
    /**
     * 添加号码
     */
    private fun addNumber() {
        Thread {
            for (i in 0..generateNum) {
                // 创建一个空的ContentValues
                values = ContentValues()
                // 向rawcontent。content——uri执行一个空值插入
                // 目的是获取系统返回的rawcontactid
                val rawcontacturi = contentResolver.insert(
                    ContactsContract.RawContacts.CONTENT_URI, values
                )
                val rawcontactid = ContentUris.parseId(rawcontacturi!!)
                values!!.clear()
                values!!.put(ContactsContract.Data.RAW_CONTACT_ID, rawcontactid)
                values!!.put(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                // 设置联系人电话号码

                val list = listOf(0,1,2,3,4,5,6,7,8,9)
                val n = 13 - number2.length
                var anyStr = ""
                for (a in 1..n) {
                    anyStr += list.random().toString()
                }
                values!!.put(ContactsContract.CommonDataKinds.Phone.NUMBER,number2+anyStr)
//                values!!.put(
//                    ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,getName()
//                )
                // 设置电话类型
                values!!.put(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                )
//                // 向联系人电话号码URI添加电话号码
                contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)
            }
            progressDialog!!.cancel()
        }.start()
    }
    class Number {
        var number: String? = null
    }
    @SuppressLint("Range")
    private fun getPhoneNumber() {
        val resolver = this@MainActivity.contentResolver
        // 获取手机联系人
        val phoneCursor = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        ) //传入正确的uri
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                val number: Number = Number()
                val phoneNumber =
                    phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) //获取联系人number
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue
                }
                number.number=(phoneNumber)
                numbers = numbers?.plus(number)
            }
        }
        phoneCursor!!.close()
    }

    private fun deleteNumber() {
        Thread {
            getPhoneNumber()
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            for (i in numbers!!.indices) {
                delContact(this@MainActivity, numbers!![i].number!!)
            }
            progressDialog!!.cancel()
        }.start()
    }

    @SuppressLint("Range")
    private fun delContact(context: Context, name: String) {
        val cursor = contentResolver.query(
            ContactsContract.Data.CONTENT_URI, arrayOf(ContactsContract.Data.RAW_CONTACT_ID),
            ContactsContract.Contacts.DISPLAY_NAME + "=?", arrayOf(name), null
        )
        val ops = ArrayList<ContentProviderOperation>()
        if (cursor!!.moveToFirst()) {
            do {
                val Id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID))
                ops.add(
                    ContentProviderOperation.newDelete(
                        ContentUris.withAppendedId(
                            ContactsContract.RawContacts.CONTENT_URI,
                            Id
                        )
                    ).build()
                )
                try {
                    contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
            cursor.close()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestReadExternalPermission() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("TAG", "READ permission IS NOT granted...")
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                Log.d("TAG", "11111111111111")
            } else {
                // 0 是自己定义的请求coude
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 0)
            }
        } else {
            Log.d("TAG", "READ permission is granted...")
        }
    }


    private fun getWrieteConstactsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_CONTACTS
                )
            ) {
                AlertDialog.Builder(this).setTitle("通讯录生成获取写入联系人权限").setPositiveButton(
                    "接受"
                ) { dialog: DialogInterface?, which: Int ->
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.WRITE_CONTACTS),
                        WRITE_PERMISSION_REQUEST
                    )
                }.setNegativeButton(
                    "拒绝"
                ) { dialog: DialogInterface?, which: Int ->
                    Toast.makeText(
                        this,
                        "用户拒绝获取联系人权限！",
                        Toast.LENGTH_SHORT
                    ).show()
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_CONTACTS),
                    WRITE_PERMISSION_REQUEST
                )
            }
        }
    }


    private fun dialog(mm: String) {
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setMessage(mm)
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    /**
     * 返回随机名字
     */
    fun getChinese(): String? {
        var str: String? = null
        val highPos: Int
        val lowPos: Int
        var random = Random()
        (176 + abs(random.nextInt(71))).also { highPos = it } //区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
        random = Random()
        lowPos = 161 + abs(random.nextInt(94)) //位码，0xA0打头，范围第1~94列
        val bArr = ByteArray(2)
        bArr[0] = highPos.toByte()
        bArr[1] = lowPos.toByte()
        try {
            str = String(bArr, charset("GB2312")) //区位码组合成汉字
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return str
    }

    fun getName():String?{
        val index: Int = Random().nextInt(Surname.size - 1)
        var name: String? = Surname.get(index) //获得一个随机的姓氏
        /* 从常用字中选取一个或两个字作为名 */
        name += if (Random().nextBoolean()) {
            getChinese() + getChinese()
        } else {
            getChinese()
        }
        return name;
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button1 -> if (number2 == "") {
                Toast.makeText(this, "请选择地区", Toast.LENGTH_SHORT).show()
            } else {
                dialog("添加联系人...")
                addNumber()
            }
            R.id.button2 -> {
                dialog("清除通讯录...")
                deleteNumber()
            }
            R.id.button3 -> {
                val intent2 = Intent()
                intent2.action = Intent.ACTION_VIEW
                intent2.data = Contacts.People.CONTENT_URI
                startActivity(intent2)
            }
            R.id.areaCodeBtn -> {
                Log.i("TAG","areaCodeBtn")
                number2 = areaCodeEdit?.text.toString()
                areaCodeTxt?.text = number2
            }
            R.id.generateNumBtn -> {
                generateNum = generateNumEdit?.text.toString().toInt()
                generateNumTxt?.text = generateNum.toString()
            }
        }
    }

}