## UI-Core框架使用说明

### 框架使用说明

- 框架结构图

  ![](C:\Users\96391\Desktop\UICore框架\框架结构.png)

- 框架结构介绍

  1. UIBase：存放基类，供各个项目使用，整个框架采用Kotlin语言编写，框架基于MVVM+Okhttp+Retrofit+Coroutine来搭建
  2. 业务层：每个项目都会有一个业务的基类，这个业务基类是基于UICore基类继承出来的，这个业务基类主要存放跟业务相关的基类东西。

### Kotlin使用说明

- Kotlin要解决的痛点
  1. Java太老(之前Android不支持Java8, 需要一些第三方工具)
  2. 太啰嗦
  3. 容易出错，例如空指针问题等
  4. Kotlin更现代化, 更方便简洁
  5. 适用于多平台的开发（Android、服务端、前端等）
- Kotlin优势
  1. 代码更加简洁
  2. 更安全: 从类型上处理空安全
  3. 和Java良好的互操作性
  4. 可以复用Java和JavaScript的库
  5. 良好的IDE支持
  6. 支持lambda, 函数式编程
  7. 很多有用的集合扩展方法
  8. 2017年5月，Google官方宣布了Kotlin作为Android开发的推荐首选语言
- 分享一个kotlin教程地址：https://github.com/mengdd/KotlinTutorials
- 接下来所有项目全部采用kotlin语言编写

### MVVM使用说明

- MVVM介绍

  MVVM是Model-View-ViewModel的简写

  Model层: 负责数据的获取

  View层: 负责视图相关（Activity、layout布局文件）,切记不要在View处理业务逻辑的代码

  ViewModel层：中间纽带层，负责业务逻辑相关的功能；负责数据的更新，当数据发生变化视图及时更新，切记不要将View传递到ViewModel层，这样违背了Google设计MVVM的初衷，Google也不推荐在ViewModel中处理View内容，ViewModel处理View直接交给LiveData来实现

- ViewModel和View之间的通信建议使用LiveData + ViewModel的方式

  1. LiveData是一个可以被观察的数据持有者，它可以通过添加观察者的方式来让其他组件观察他的变更。
  2. LiveData遵从应用程序的生命周期，（如果LiveData的观察者已经是销毁状态，LiveData就不会通知该观察者）
  3. 项目暂时不采用DataBinding的通信方式，原因：如果出现bug情况，DataBinding难以调试

- MVVM优点

  1. 实现了数据和视图的双向绑定，极大的简化代码
  2. LiveData更好的解决MVVM之间的通信问题，并且感知组件的生命周期，有效避免内存泄漏

### Okhttp+Rretrofit+Coroutine使用说明

- coroutine介绍

  1. coroutine用来做异步和非阻塞任务, 主要优点是代码可读性好, 不用回调函数
  2. coroutine可以理解为轻量级的线程. 多个协程可以并行运行, 互相等待, 互相通信. 协程和线程的最大区别就是协程非常轻量(cheap), 我们可以创建成千上万个协程而不必考虑性能
  3. 默认情况下, 协程运行在一个共享的线程池里, 线程还是存在的, 只是一个线程可以运行多个协程, 所以线程没必要太多

- Activity/Fragment & coroutine

  1. 在Android中, 可以把一个屏幕(Activity/Fragment)和一个CoroutineScope关联, 这样在Activity或Fragment生命周期结束的时候, 可以取消这个scope下的所有协程, 好避免协程泄漏
     利用CoroutineScope来做这件事有两种方法: 创建一个CoroutineScope对象和activity的生命周期绑定, 或者让activity实现CoroutineScope接口

     - 方法1：持有scope引用

       ```
       class Activity {
           private val mainScope = MainScope()
           
           fun destroy() {
               mainScope.cancel()
           }
       } 
       ```

     - 方法2：实现接口

       ```
       class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {
           fun destroy() {
               cancel()
           }
       }
       ```

- ViewModel & coroutine

  1. Google目前推广的MVVM模式, 由ViewModel来处理逻辑, 在ViewModel中使用协程, 同样也是利用scope来做管理.

  2. ViewModel在屏幕旋转的时候并不会重建, 所以不用担心协程在这个过程中被取消和重新开始.

     - 方法1: 自己创建scope

       ```
       private val viewModelJob = Job()

       private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
       ```

       默认是在UI线程.
       CoroutineScope的参数是CoroutineContext, 是一个配置属性的集合. 这里指定了dispatcher和job.
       在ViewModel被销毁的时候：

       ```
       override fun onCleared() {
           super.onCleared()
           viewModelJob.cancel()
       }
       ```

       这里viewModelJob是uiScope的job, 取消了viewModelJob, 所有这个scope下的协程都会被取消.
       一般CoroutineScope创建的时候会有一个默认的job, 可以这样取消：

       ```
       uiScope.coroutineContext.cancel()
       ```

     - 方法2: 利用viewModelScope

       如果我们用上面的方法, 我们需要给每个ViewModel都这样写. 为了避免这些boilerplate code, 我们可以用viewModelScope.
       注: 要使用viewModelScope需要添加相应的KTX依赖.
       For ViewModelScope, use androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0-beta01 or higher.
       viewModelScope绑定的是Dispatchers.Main, 会自动在ViewModel clear的时候自动取消.
       用的时候直接用就可以了：

       ```
       class MainViewModel : ViewModel() {

           private fun makeNetworkRequest() {
               viewModelScope.launch(Dispatchers.IO) {
                   //TODO
               }
           }
       }
       ```

       所有的setting up和clearing工作都是库完成的.

### BaseActivity/BaseFragment使用说明

- BaseActivity类使用说明

  1. 最简单实现

     - 实现initViewModel()和getLayoutResId()方法

     - 如果不需要ViewModel则可使用默认的DefaultViewModel

       ```
       class SettingActivity : BaseMarriageActivity<DefaultMarriageViewModel>() {

           override fun getLayoutResId(): Int {
               return R.layout.activity_setting
           }

           override fun initViewModel(): DefaultMarriageViewModel {
               return ViewModelProvider(this).get(DefaultMarriageViewModel::class.java)
           }

           override fun initView(rootView: View, savedInstanceState: Bundle?) {
               setToolBarTitle("设置")
           }

       }
       ```

     - 注意initViewModel()不要直接new ViewModel()，直接采用ViewModelProvider(this).get(DefaultMarriageViewModel::class.java)，可以规避页面旋转等导致重复创建ViewModel()问题

  2. 扩展方法

     - enableImmersion()：是否使用沉浸式状态栏，默认true

     - enbaleFixImmersionAndEditBug()：默认false，如果布局底部有Edittext，此时如果使用沉浸式状态会有BUG，此方法就是为了解决此场景，例如聊天页，发布这样的界面则需要将此方法设置为true。

     - enabledVisibleToolBar()：是否展示Toolbar，默认true，默认的Toolbar框架集成了返回键、标题、右边文字，右边图片，下划线，并提供了几套默认的toolbar样式供开发选择（渐变黄、渐变红、黄色、红色、白色、灰色、紫色、黑色），如果不满足需求则可以自定义Toolbar，如果要自己实现toolbar，直接重写getCustomToolBarLayoutResId()，将自定义toolbar的布局设置进去就OK

     - initCommonToolBarBg()：如果使用默认的toolbar，则此方法生效，设置标题栏背景颜色，默认白色，如果要替换其他颜色，直接返回ToolBarView.ToolBarBg.xx对应颜色就可以

       ```
       override fun initCommonToolBarBg(): ToolBarView.ToolBarBg {
               return ToolBarView.ToolBarBg.GRAY
           }
       ```

     - onClickToolBarView()：如果使用默认的toolbar，则此方法生效，监听Toolbar对应的点击事件，目前有，返回键事件、标题事件、右边文本事件、右边图片事件

       ```
       override fun onClickToolBarView(view: View, event: ToolBarView.ViewType) {
               super.onClickToolBarView(view, event)
               if (event == ToolBarView.ViewType.RIGHT_TEXT) {
               	//TODO右边按钮点击事件处理
               }
           }
       ```

     - enabledDefaultBack()：如果使用默认的toolbar，则此方法生效，支持默认返回按钮和事件，activity默认true，fragment默认false，如果为true业务层不再需要监听返回键事件，框架默认处理好了

     - showProgressDialog()：在页面中展示加载中弹窗，使用场景：按钮点击事件处理网络请求等

     - hideProgressDialog()：在页面中隐藏加载中弹窗

     - buildCustomStatusLayoutView()：如果不是用默认的空布局/错误布局样式则实现此方法来自定义布局样式和点击事件

       ```
       override fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder {
               val emptyView = LayoutInflater.from(context).inflate(R.layout.layout_status_empty_scroll, null)
               val linearEmpty = emptyView.findViewById<LinearLayout>(R.id.linear_empty)
               linearEmpty.setOnClickListener {
                   statusLayoutRetry(emptyView, StatusLayoutType.STATUS_EMPTY)
               }
               builder.setEmptyLayout(emptyView)
               return builder
           }
       ```

     - statusLayoutRetry()：默认空布局/错误布局回调点击事件

       ```
       override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
               if (StatusLayoutType.STATUS_LOAD_ERROR == status) {
                   hideStatusLayout()
                   viewModel.getDepositInfo()
               }
           }
       ```

     - showEmptyLayout()：展示默认空布局

     - showLoadingLayout()：展示默认加载中布局

     - showLoadErrorLayout()：展示默认错误布局

     - showCustomLayout()：展示自定义布局

     - hideStatusLayout()：隐藏空布局/错误布局

     - getDefaultEmptyLayout()：获取默认的空布局View，可设置默认空布局对应的图片/文字

       ```
       getDefaultEmptyLayout()?.setTipText("内容空空如也~")
        getDefaultEmptyLayout()?.setImage(R.drawable.ic_status_layout_load_empty)
       ```

     - getDefaultLoadErrorLayout()：获取默认的错误布局View，可设置默认错误布局对应的图片/文字

       ```
       getDefaultLoadErrorLayout()?.setTipText("出错了~")
        getDefaultLoadErrorLayout()?.setImage(R.drawable.ic_status_layout_load_error)
       ```

     - initView()：对view进行初初化设置。加载数据，显示进度条等。

     - initData()：可在此方法调用获取数据接口

     - initLazyData()：在onUserVisible方法后调用，如果需要UI先绘制完成再做数据处理，则可以使用此方法

     - initUIChangeLiveDataCallBack()：注册ViewModel与View的契约UI回调事件

     - showToast()：吐司

     - finishAc()：关闭页面

     - registorUIChangeLiveDataCallBack()：处理viewmodel对应的LiveData回调事件

     - pushFragmentToBackStack()：切换跳转Fragment

     - popTopFragment(data: Any?)：返回上一个Fragment(可携带参数回调给上一个Fragment)

       场景：FragmentA跳转到FragmentB，FragmentB返回FragmentA的时候需要携带参数给FragmentA，这时候可以这样使用

       FragmentA跳转到FragmentB代码如下：

       ```
        val args = Bundle()
        args.putString(Extra.arg1, "标题")
       val baseActivity = activity as BaseActivity<*>
        baseActivity.pushFragmentToBackStack(EditPersonInfoFragment::class.java, args)
       ```

       FragmentB获取FragmentA携带参数内容

       ```
       override fun initView(rootView: View, savedInstanceState: Bundle?) {
           if (dataIn is Bundle) {
               val bundle = dataIn as Bundle
               title = bundle.getString(Extra.arg1, "")
               setToolBarTitle(title)
           }
        }
       ```

       FragmentB返回FragmentA携带参数给FragmentA代码如下：

       ```
       override fun onClickToolBarView(view: View?, event: ToolBarView.ViewType?) {
               if (event == ToolBarView.ViewType.RIGHT_TEXT) {       
                   val baseActivity = activity as BaseActivity<*>
       				 val args = Bundle()
                        args.putString(Extra.arg1, "夏天")
                        args.putString(Extra.arg2, "泉州")
                        baseActivity.popTopFragment(args)
               }
       }
       ```

       FragmentA获取FragmentB携带参数内容代码如下：

       ```
       override fun onBackWithData(content: Any?) {
               if (content != null) {
                   if (content is Bundle) {
                       val bundleStr = contentUserName as Bundle
                       var title = bundleStr.getString(Extra.arg1)
                       var city = bundleStr.getString(Extra.arg2)
                           tvTitle.text = title
                           tvCity.text = city
                   }
               }
           }
       ```

- BaseFragment类使用说明

  1. 泄露 Fragment 中的 LiveData 观察者问题

     Fragment 具有复杂的生命周期，当一个 Fragment 与其宿主 Activity 取消关联（执行 Fragment#onDetach()），然后重新关联（执行 Fragment#onAttach()）时，实际上这个 Fragment 并不总是会被销毁（执行 Fragment#onDestroy()）。例如在配置变化时，被保留（Fragment#setRetainInstance(true)）的 Fragment 不会被销毁。这时，只有 Fragment 的视图会被销毁（Fragment#onDestroyView()），而 Fragment 实例没有被销毁，因此不会调用 Fragment#onDestroy() 方法，也就是说 Fragment 作为 LifecycleOwner 没有到达已销毁状态 （Lifecycle.State#DESTROYED）。
     这意味着，如果我们在 Fragment#onCreateView() 及以后的方法（通常是 Fragment#onActivityCreated()）中观察 LiveData，并将 Fragment 作为 LifecycleOwner 传入就会出现问题。

     代码如下：

     ```
     viewModel.showProgressDialogEvent.observe(this, Observer { _ ->
          showProgressDialog()
     })
     ```

     每次当 Activity 重新关联 Fragment 时，我们都会传递一个新的相同的观察者实例，但是 LiveData 不会删除以前的观察者，因为 LifecycleOwner（即 Fragment）没有达到已销毁状态。这最终会导致越来越多的相同观察者同时处于活动状态，从而导致 Observer#onChanged() 方法也会被重复执行多次。

     解决方案：

     通过 Fragment#getViewLifecycleOwner() 或 Fragment#getViewLifecycleOwnerLiveData() 方法获取 Fragment 的视图（View）生命周期，而不是 Fragment 实例的生命周期，这两个方法是在 Support-28.0.0 和 AndroidX-1.0.0 中添加的，这样，LiveData 就会在每次 Fragment 的视图销毁时移除观察者。

     代码如下：

     ```
     viewModel.showProgressDialogEvent.observe(viewLifecycleOwner, Observer { _ ->
           showProgressDialog()
     })
     ```

  2. 其他情况BaseFragment和BaseActivity使用一致，这里就不在阐述。

### BaseListActivity/BaseListFragment使用说明

- BaseListActivity类使用说明

  1. 默认使用的layout

     ```
     override fun getLayoutResId(): Int {
     	return R.layout.include_default_recyclerview_list
     }
     ```

  2. initAdapter()：初始化适配器

  3. getLayoutManager()：配置LayoutManager，默认LinearLayoutManager

  4. getItemDecoration()：配置ItemDecoration

  5. setBackgroundColor()：设置列表背景颜色

  6. autoRefresh()：自动刷新，调用此方法列表会开启自动刷新下拉动画，最终回调到doHttpRequest()，业务层可以在doHttpRequest()处理数据请求

  7. doHttpRequest()：发起Http请求

  8. enableLoadMoreWhenContentNotFull()：设置在内容不满一页的时候，是否可以上拉加载更多，默认false

  9. enabledUsedRefresh()：是否使用下拉刷新，默认true

  10. enabledUsedLoadMore()：是否使用上拉加载，默认true

  11. enabledUsedAdapterLoadMore()：是否使用adapter样式的加载更多，默认true。目前支持adapter样式的加载更多和SmartRefreshLayout样式的加载更多。注意当使用SmartRefreshLayout加载更多时，当不满一屏时，不会自动加载更多，需要手动上拉加载更多。而使用adapter样式，则不满一屏可以自动加载更多。

  12. startPage()：开始页，是从0，还是1开始.默认从1开始

  13. isRefresh()：是否是下拉刷新状态

  14. isFirstPage()：是否是第一页

  15. getPage()：获取当前页数

  16. setAdapterData()：填充adapter数据，这边已经处理好了下拉刷新和加载更多两种状态，业务层无需再处理

  17. setNewData()：设置新的数据实例，替换原有内存引用

  18. addData()：添加数据

  19. loadMoreFail()：加载更多出错布局处理

  20. loadMoreComplete()：加载更多完成

  21. loadMoreEnd()：加载更多结束

  22. loadComplete()：加载完成

- BaseListFragment使用和BaseListActivity一致，这里就不在阐述。

### SimpleListActivity/SimpleListFragment使用说明

- SimpleListActivity类使用说明

  1. 最简单实现

     ```
     class InterActActivity : SimpleListActivity<SystemaicMessageBean, InterActAdapter, InterActActivity.InterActViewModel>() {

         override fun initView(rootView: View, savedInstanceState: Bundle?) {
             super.initView(rootView, savedInstanceState)
             setToolBarTitle("互动通知")
         }

         override fun initAdapter(): InterActAdapter {
             return InterActAdapter()
         }

         override fun initViewModel(): InterActViewModel {
             return InterActViewModel()
         }

         class InterActViewModel : SimpleListViewModel<SystemaicMessageBean>() {
             override suspend fun requestData(offset: Int, length: Int): CommonResponse<MutableList<SystemaicMessageBean>> {
                 return MessageRepository.getInteractiveMessage(offset, length)
             }
         }

     }
     ```

- SimpleListFragment使用和SimpleListActivity一致，这里就不在阐述。

### CommonAlerDialog使用说明

- CommonAletDialog类使用说明

  1. title/titleResId：设置标题
  2. content/contentResId：设置内容
  3. hint/hintResId：设置编辑框提示语
  4. leftBtnText/leftBtnTextResId：设置左边按钮文字内容
  5. leftBtnColor/leftBtnColorInt：设置左边按钮文字颜色
  6. rightBtnText/rightBtnTextResId：设置右边按钮文字内容
  7. rightBtnColor/rightBtnColorInt：设置右边按钮文字颜色
  8. confirmBtnText/confirmBtnTextResId：设置单个按钮文字内容
  9. confirmBtnColor/confirmBtnColorInt：设置单个按钮文字颜色
  10. openBackGroundColor：设置遮罩层背景颜色
  11. outSideDismiss：点击外部无法dismiss
  12. backPressEnable：点击返回键无法dismiss
  13. outSideTouchable：外部点击事件无法响应

- CommonAlertDialog的简单使用

  1. 通用弹窗Confirm支持：标题+内容+左边按钮+右边按钮

     ​		    			  内容+左边按钮+右边按钮

     使用方法如下

     ```
      CommonAlertDialog(this).apply {
            type = CommonAlertDialog.DialogType.Confirm
            title = "我是标题"
            content = "我是内容"
            leftBtnText = "左边按钮"
            rightBtnText = "右边按钮"
            listener = object : DialogClickListener.DefaultLisener() {
            override fun onRightBtnClick(view: View) {
     			//TODO 实现右边按钮点击事件
            }
         }
       }.show()
     ```

  2. 通用弹窗SINGLE支持：标题+内容+按钮

     ​		    			内容+按钮

     使用方法如下

     ```
     CommonAlertDialog(this@LiveRoomActivity).apply {
               type = CommonAlertDialog.DialogType.SINGLE
               title = "我是标题"
               content = "我是内容"
               confirmBtnText = "按钮"
               listener = object : DialogClickListener.DefaultLisener() {
               override fun onConfirmBtnClick(view: View) {
               		//TODO 按钮点击事件
               }
           }
      }.show()
     ```

  3. 通用弹窗Edit支持：标题+编辑框+左边按钮+右边按钮

     ​		    		  编辑框+左边按钮+右边按钮

     使用方法如下

     ```
     CommonAlertDialog(activity!!).apply {
           type = CommonAlertDialog.DialogType.Edit
           title = "我是标题"
           hint = "编辑框提示语"
           leftBtnText = "左边按钮"
           rightBtnText = "右边按钮"
           listener = object : DialogClickListener.DefaultLisener() {
                override fun onLeftBtnClick(view: View) {
                         //TODO 左边按钮点击事件
                 }
                 override fun onRightEditBtnClick(view: View, content: String?) {
                         //TODO 右边按钮点击事件
                 }
            }
     }.show()
     ```


### BaseWebActivity->SonicWebActivity->CommonWebActivity使用说明

- BaseWebActivity介绍
  1. BaseWebActivity基于AgentWeb的二次封装，每个项目的标题栏和指示器颜色都可能不一致，这里抽象出来供业务层配置，满足业务需求
  2. BaseWebActivity默认配置了WebChromeClient和WebViewClient，并集成了在网页浏览文件图片功能，如果未满足需求也可以自定义
  3. 监听BaseWebActivity的生命周期，销毁时及时清除缓存
- SonicWebActivity介绍
  1. Sonic：腾讯出品的一个轻量级的高性能的Hybrid框架，专注于提升页面首屏加载速度
  2. SonicWebActivity继承BaseWebActivity，将Sonic集成到我们框架中来，加速网页打开速度，提升用户体验
- CommonWebActivity介绍
  1. CommonWebActivity供业务层调用，继承SonicWebActivity
  2. 与JS交互直接都在AndroidInterface中处理
  3. 前端可以直接控制Android网页标题栏，修改标题栏对应的标题右边图片右边文字返回键，更好的兼容业务

### 基础控件、通用工具类、Core、GlideUtils使用说明

- 基础控件
  1. RoundImageView->圆角矩形ImageView
  2. RoundTextView->圆角矩形TextView
  3. RoundFrameLayout->圆角矩形FrameLayout
  4. RoundLinearLayout->圆角矩形LinearLayout
  5. RoundRelativeLayout->圆角矩形RelativeLayout
  6. DotView->indocator指示器
  7. WheelView->3d滚轮控件
  8. EasySwipeMenuLayout->侧滑删除控件
  9. SlidingTabLayout->滑动切换的TabLayout
  10. MsgView->消息红点未读数
  11. ToggleButton->开关空间
- 通用工具类
  1. SpanStrUtil->分段文字（颜色、字体、大小）处理
  2. BigDecimalUtils->数学加减乘除工具类
  3. DateUtil->日期工具类
  4. DoubleClickUtils->控制点击，避免瞬间点击多下
  5. ThreadPoolManager->线程池管理类
  6. 引入强大的BlankJ工具类->具体文档参照：https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md
- Decoration
  1. DividerDecoration->列表分割线处理类
  2. SpaceDecoration->列表间距处理类
- Core
  1. Core.getContext()：提供全局的上下文
  2. Core.getHandler()：提供全局的主线程Handler
- GlideUtils
  1. GlideUtils.load()：加载图片
  2. GlideUtils.loadNoPlace()：加载图片（不显示加载中、错误的默认加载图）
  3. GlideUtils.loadRound()：加载圆角矩形图片
  4. GlideUtils.loadRoundNoPlace()：加载圆角矩形图片（不显示加载中、错误的默认加载图）
  5. GlideUtils.loadCicle()：加载圆形图片
  6. GlideUtils.loadCicleNoPlace()：加载圆形图片（不显示加载中、错误的默认加载图）
  7. GlideUtils.loadBlur()：加载毛玻璃图片
  8. GlideUtils.loadBlurNoPlace()：加载毛玻璃图片（不显示加载中、错误的默认加载图）
  9. GlideUtils.loadGif()：加载Gif图片
  10. GlideUtils.loadGifNoPlace()：加载Gif图片（不显示加载中、错误的默认加载图）
- WebSocketManager
  1. websocket初始化
  2. websocket断线重连处理