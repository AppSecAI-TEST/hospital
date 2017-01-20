# hs-医院业务模型

# 目标

- 采用新的方式做一个概念清晰、结构简单、能够演进的支撑医院业务的内核。期望这个内核是延续型的，并能够带动以此内核成长起的软件系统的高质量，并对人员分工产生实质性的影响。
- 高质量的内涵：
- 1、通过简化业务和赋予软件系统更多的职责提高医生和护士的工作效率。
- 2、软件系统可以采用较低的成本跟上人思考业务的变化。
- 关于新的方式的说明：
- 采用了经典RUP相关的工程方法。该方法可以在不同的层面上实现：
- 1、分离关注，可以独立思考又实现相互联系
- 2、可视化的表达对业务、模型、软件结构的理解和设计
- 3、能够从方法上实现对理解和设计进行验证
- 关于简化业务和赋予软件系统更多的职责的思路有：
- 1、医嘱开立、核对人工干预，分解、发送（基于规则）、收费自动完成，将更多的业务（如：出院登记、结算等）塑造为医嘱执行，并依靠软件系统完成更多的职责
- 2、将部分病历书写视为医嘱执行，通过简单信息的录入自动生成符合病案室归档的格式
- 3、将与医嘱执行有关的角色塑造为指令执行者，采用统一的待办项列表的形式办理业务，在正常流程下减少对业务和软件复杂度的感知

# 设计原则和思路

-与管理有关的逻辑尽量在application中编写，与领域有关的逻辑在domain中编写
-通过EDA实现不同稳定性区块间的协作，如医嘱执行和收费
-通过识别核心概念，及其建立它们之间的关联和协作确定核心主体结构和逻辑分布
-通过继承手段实现核心逻辑的再次分布
-通过在核心概念的协作中嵌入恰当的回调方法实现对未来领域逻辑的扩展
-通过EDA方式实现基于核心概念行为和状态变迁的大量其他逻辑的扩展
-初期领域概念的建立必须清晰，不能妥协！
-什么是管理和协作逻辑，什么是领域逻辑需要在分析、设计和编码过程中不断实践、思考和总结（会开新的章节总结）
-严格保证领域组件间的单向依赖关系，不能出现环状依赖
-初期要保证业务、需求、设计文档和代码的一致性

# 具体决策

- 1、采用eclipse\jdk8\maven\springboot构建；
- 2、将应用划分为platform、domain、application、listener、portal、mobile、rest、test八个工程；
- 3、包结构以com.neusoft.hs开始；
- 4、view采用springboot推荐的thymeleaf构建；
- 5、持久化采用springdata；
- 6、主键生成采用uuid；
- 7、核心对象关系采用manytoone和onetomany构建（采用lazyload策略）；
- 8、数据库采用mysql；
- 9、采用Repository模式，通过在IdEntity中调用平台中的ApplicationContextUtil获取Repo；
- 10、采用可捕获异常来定义业务异常，并建立异常树来分类业务异常；
- 11、通过实体关系获取实体将不再通过Repo来获取，只通过实体上的引用获取；
- 12、当通过定制条件获取实体时使用Repo；
- 13、除了创建实体外，其他操作尽量在DomainService后通过Repo.find实例化实体；
- 14、领域逻辑尽量附着在实体上，其次是DomainService；
- 15、非领域逻辑不允许被DomainService和Entity依赖；
- 16、领域事件由DomainService抛出；
- 17、通过@Async可实现异步监听；
- 18、领域模型中错误处理代码应为正常代码量的n倍；
- 19、领域模型需配备n倍场景的单元测试代码；
