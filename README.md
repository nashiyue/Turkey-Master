# Turkey-Master
Turkey系统是一个任务分发系统，底层数据存储是Panda图存储系统；
Turkey-Client:
    １．向Master提交查询job请求
    　　具体的配置信息以xml文件的信息进行job配置

Turkey-Master:
    １. 接收client提交的job请求
    ２. 为job分配唯一的jobId，然后加入到job队列进行调度
    ３. 按照配置文件的要求，将job拆分若干个Task，并根据特定的调度策略进行分发
    ４. 维护slave的服务状态，接收心跳信息
    ５. 根据特定的合并策略，将slave返回的结果进行合并操作，并输出到指定位置
    ６. 维护特定时间段内，所有job的具体信息，执行状态等
    
Turkey-Slave
    1. 接收来自Master的Task，并与Panda图存储系统交互
    2. 将交互后的结果，返回给Master
    3. 向Master持续发送心跳信息
