# Map 总结

![Map.png](http://ww1.sinaimg.cn/large/70dc5b5cgy1g759iehu7qj217c0fmdgi.jpg)

在 jdk 集合中哈希表的实现方式具体有如下几种：

（1）Hashtable 是基于数组实现的线程安全的哈希表，内部通过 synchronized 关键字来实现，不允许 key、value 为 null，默认容量为 11， 负载因子为 0.75，如果尝试 null 值存放到 Hashtable 中，会抛出NullPointerException 异常；判断两个 key 相等的条件为：两个 key 通过 equals 方法返回 true，且两个 key 的哈希值相等，则认为两个 key 相等。

（2）Properties 是 Hashtable 的子类，它的 key、value 都是字符串类型，通常用于将属性文件；它判断两个key 相等的标准同 Hashtable 一致。

（3）HashMap 是基于数组+单链表+红黑树实现的非线程安全的哈希表，默认容量为 16，负载因子为 0.75，允许 key、value为 null，最多有一个键值对的 key 为 null，但允许多个键值对的 value 为 null；HashMap 判断两个 key 相等的条件为：两个 key 通过 equals 方法返回 true ，且两个 key 的哈希值相等，则认为两个 key 相等。

（4）LinkedHashMap 是 HashMap的子类，是基于数组+单链表+红黑树+双链表实现的非线程安全的哈希表，在 HashMap 的基础上增加了双向链表的数据结构，用于维护 LinkedHashMap 的迭代顺序，因此，LinkedHashMap 是一个有序的哈希表，默认的迭代顺序为插入键值对的顺序，但可以通过构造方法指定 LinkedHashMap 的迭代顺序为访问顺序。

（5）TreeMap 是基于红黑树实现，在 TreeMap 没有扩容概念，不允许 key为 null 值，但允许 value 为 null 值；TreeMap 会保证键值对的有序性，默认的排序方式为自然排序，也就是使用 Comparable 接口对 key 进行排序，当然也可以使用比较器 Comparator 对 key 进行排序，它判断两个 key 相等的标准是：两个 key 通过 compareTo 或者 compare 方法返回 0 ，就认为两个 key 相等。

如果自定义类作为 TreeMap 的 key ，则必须重写自定义类的 equals 、compareTo 方法，且他们的返回值必须保持一致，即当 equals 方法返回 true 时，通过 compareTo 方法必须返回 0，否则 TreeMap 不能正常工作。

（6）WeakHashMapMap 是基于弱键实现的哈希表，默认容量为 16，默认负载因子为 0.75，底层实现会把 key 为 null  的键包装成一个 Object 对象，如果 WeakHashMap 对象的键所引用的对象没有被其他强引用对象引用，那么这些弱键实现的 key 可能会在垃圾回收器工作时被回收，从而使得 WeakHashMap 自动删除这些弱键对应的键值对；判断两个 key 相等的标准是：两个 key 的哈希值相等且通过 euqals 方法返回 true，且 hash 值相等，那么这两个 key 就相等；扩容时会自动将容量扩大为原来的两倍；

需要注意的是，虽然 WeakHashMap 名称中带有 HashMap，但是 WeakHashMap 与 HashMap 没有任何关系。

（7）IdentityHashMap 的数据结构就是一个数组（Object[]），键值对的 key、value 存储在相邻的两个位置，默认容量为 32，最小容量为 4，默认负载因子为 2/3，但指定初始容量 initiCapacity 时，底层会创建一个长度为 2 * initCapacity 大小的 Object[] 数组；允许 key、value 为 null，判断两个 key 相等的标准为：两个 key 通过 ”==“ 返回 true，且哈希值相等（与普通的哈希表不同，哈希值是通过 System#identityHashCode 方法来实现的），则认为两个 key 相等；

IdentityHashMap 是采用线性探测法来解决哈希冲突，如果探测到数组的末尾，则从 0 开始重新探测，也就是在解决哈希冲突时，可以把 Object[] 数组看成一个环。

（8）EnumMap 是一个与枚举类一起使用的哈希表，底层的数据结构是一个 Object[] 类型的数组；其中键值对的所有 key 必须是同一个枚举类的枚举值，通过使用枚举值的 oridinal 作为索引值确定在 EnumMap 中的位置。

## 哈希表

**数组：查找快，新增、删除慢**
采用一段连续的存储单元来存储数据。对于指定下标的查找，时间复杂度为 $O(1)$；通过给定值进行查找，需要遍历数组，逐一比对给定关键字和数组元素，时间复杂度为 $O(n)$，当然，对于有序数组，则可采用二分查找，插值查找，斐波那契查找等方式，可将查找复杂度提高为 $O(logn)$；对于一般的插入删除操作，涉及到数组元素的移动，其平均复杂度也为 $O(n)$。

**线性链表：新增、删除快，查找慢**
对于链表的新增，删除等操作（在找到指定操作位置后），仅需处理结点间的引用即可，时间复杂度为 $O(1)$，而查找操作需要遍历链表逐一进行比对，复杂度为 $O(n)$。

**二叉树：自平衡的话，新增、删除、查找都不快不慢**
对一棵相对平衡的有序二叉树，对其进行插入，查找，删除等操作，平均复杂度均为 $O(logn)$。

**哈希表：添加，删除，查找等操作都很快 (数组+链表)**
相比上述几种数据结构，在哈希表中进行添加，删除，查找等操作，性能十分之高，不考虑哈希冲突的情况下，仅需一次定位即可完成，时间复杂度为 $O(1)$，接下来我们就来看看哈希表是如何实现达到惊艳的常数阶 $O(1)$ 的。

数据结构的物理存储结构只有两种：<font color=red>顺序存储结构和链式存储结构（像栈，队列，树，图等是从逻辑结构去抽象的，映射到内存中，也这两种物理组织形式）</font>，而在上面我们提到过，在数组中根据下标查找某个元素，一次定位就可以达到，哈希表利用了这种特性。

![哈希表结构.png](http://ww1.sinaimg.cn/large/70dc5b5cgy1g77kiu7dvij20df09vmxn.jpg)

查找过程：
1、我们通过把当前元素的关键字通过某个函数（hash 算法取模 **存储位置 = f(关键字)**）映射到数组中的某个位置，通过数组下标一次定位就可完成操作；

> 如果两个不同的元素，通过哈希函数得到相同的存储地址的现象称为哈希冲突，也叫哈希碰撞；

2、哈希冲突（哈希碰撞）：<font color=red>哈希函数的设计至关重要，好的哈希函数会尽可能地保证 计算简单和散列地址分布均匀，</font>但是，数组是一块连续的固定长度的内存空间，再好的哈希函数也不能保证得到的存储地址绝对不发生冲突。

那么哈希冲突如何解决呢？

哈希冲突的解决方案有多种：<font color=red>开放定址法（发生冲突，继续寻找下一块未被占用的存储地址），再散列函数法，链地址法</font>，而 HashMap 采用了链地址法，也就是数组+链表的方式。

## 是否允许空键/空值

可以通过下面的表格来说明各个哈希表的 key、value 是否允许为 null 值。

|       `Map`实现类       |     `key`      |    `value`     |         说明         |
| :---------------------: | :------------: | :------------: | :------------------: |
|       `Hashtable`       | 不允许为`null` | 不允许为`null` |       线程安全       |
|        `HashMap`        |  允许为`null`  |  允许为`null`  |      线程不安全      |
|        `TreeMap`        | 不允许为`null` |  允许为`null`  | 线程不安全，且是有序 |
|      `WeakHashMap`      |  允许为`null`  |  允许为`null`  | 线程不安全，弱键实现 |
|    `IdentityHashMap`    |  允许为`null`  |  允许为`null`  |      线程不安全      |
|        `EnumMap`        | 不允许为`null` |  允许为`null`  |      线程不安全      |
|   `ConcurrentHashMap`   | 不允许为`null` | 不允许为`null` |       线程安全       |
| `ConcurrentSkipListMap` | 不允许为`null` |  允许为`null`  |       线程安全       |

## 判断 key 相等

- Hashtable、HashMap、LinkedHashMap、WeakHashMap 判断两个 key 是否相等是通过 equals 方法返回 true，且两个 key 的哈希值相等，则认为两个 key 相等；
- TreeMap 判断两个 key 是都相等是通过 compareTo 或者 compare 方法返回 0 ，就认为两个 key 相等；
- IdentityHashMap 判断两个 key 是否相等是两个 key 通过 ”==“ 返回 true，且哈希值相等，则认为两个 key相等；
- ConcurrentSkipListMap 通过 Comparable 或者 Comparator 判断两个 key 相等；
- EnumMap 内部通过使用枚举值的 oridinal 判断两个 key 相等；

## 哈希碰撞的解决方法

- 开放定址法
- 拉链法
- 再哈希法
- 建立公共的溢出区

（1）开放定址法

从发生冲突的那个单元起，按照一定的次序，从哈希表中找到一个空闲的单元。然后把发生冲突的元素存入到该单元的一种方法。开放定址法需要的表长度要大于等于所需要存放的元素。

在开放定址法中解决冲突的方法有：线行探查法、平方探查法、双散列函数探查法。

开放定址法的缺点在于删除元素的时候不能真的删除，否则会引起查找错误，只能做一个特殊标记。只到有下个元素插入才能真正删除该元素。

（2）拉链法

拉链法的思路是将哈希值相同的元素构成一个同义词的单链表，并将单链表的头指针存放在哈希表的第i个单元中，查找、插入和删除主要在同义词链表中进行，链表法适用于经常进行插入和删除的情况。

与开放定址法相比，拉链法具有如下优点：

- 拉链法处理冲突简单，且无堆积现象，即非同义词决不会发生冲突，因此平均查找长度较短；

- 由于拉链法中各链表上的结点空间是动态申请的，故它更适合于造表前无法确定表长的情况；
- 开放定址法为减少冲突，要求装填因子 α 较小，故当结点规模较大时会浪费很多空间；而拉链法中可取 α≥1，且结点较大时，拉链法中增加的指针域可忽略不计，因此节省空间；
- 在用拉链法构造的散列表中，删除结点的操作易于实现，只要简单地删去链表上相应的结点即可。而对开放地址法构造的散列表，删除结点不能简单地将被删结 点的空间置为空，否则将截断在它之后填人散列表的同义词结点的查找路径。这是因为各种开放地址法中，空地址单元(即开放地址)都是查找失败的条件。因此在 用开放地址法处理冲突的散列表上执行删除操作，只能在被删结点上做删除标记，而不能真正删除结点。

拉链法的缺点是：指针需要额外的空间，故当结点规模较小时，开放定址法较为节省空间，而若将节省的指针空间用来扩大散列表的规模，可使装填因子变小，这又减少了开放定址法中的冲突，从而提高平均查找速度。

（3）再哈希法

就是同时构造多个不同的哈希函数：
 Hi = RHi(key)   i= 1,2,3 ... k;
 当H1 = RH1(key)  发生冲突时，再用H2 = RH2(key) 进行计算，直到冲突不再产生，这种方法不易产生聚集，但是增加了计算时间

（4）建立公共的溢出区

将哈希表分为公共表和溢出表，当溢出发生时，将所有溢出数据统一放到溢出区

## 各种Map 解决冲突的方法

- HashMap：采用 “拉链法” 解决哈希冲突；不是线程安全的，多用于单线程环境中；

- LinkedHashMap：继承 HashMap，因此，也是基于 “拉链法” 解决哈希冲突，存储需要保证插入顺序的单线程环境中；

- Hashtable：采用 “拉链法” 实现的哈希表，一般用于多线程环境中，现在使用得比较少，如果要保证多线程环境中安全使用哈希表，推荐使用 ConcurrentHashMap；

- WeakHashMap：采用 “拉链法” 实现的哈希表，它一般也用于单线程程序中，WeakHashMap 中的键是 “弱键”，当 “弱键” 被GC回收时，它对应的键值对也会被从 WeakHashMap 中删除；

- TreeMap 是有序的哈希表，它是通过红黑树实现的，它一般用于单线程中存储有序的映射；

- IdentityHashMap 是基于数组实现的哈希表，采用 ”开放定址“ 方法及解决哈希冲突，内部使用一个 Object[] 数组存储元素，key-value 存储在相邻的两个位置，负载因子为 $2/3$。

## key 排序

- LinkedHashMap 默认按照元素的插入顺序进行排序，也可以按照元素的访问顺序进行排序
- TreeMap、ConcurrentSkipListMap 按照 Comparable 或者 Comparator 来对 key 进行排序

## 遍历方式

Map 的遍历方式有三种：

- entrySet 方式：一次性的获取 key、value
- keySet 方式：获取所有的 key
- values 方式：获取到所有的 value

jdk 1.8 之后提供了 lambda 表达式的遍历方式，因此如果可以用 lambda 表达式遍历，那就直接选择即可。

```java
//entrySet方式：key和value一次性都拿出来
for (Entry<String, String> entry: map.entrySet()) {
    key = entry.getKey();
    value = entry.getValue();
}

//keySet方式：先拿出key，再去拿value
for (String key : map.keySet()) {
    value = map.get(key);
}

//values方式：当只需要value的时候，这种方式才合适
for (String value : map.values()) {

}
```

如果你是遍历 HashMap：

- 遍历既需要 key 也需要 value 时：keySet 与 entrySet 方法的性能差异取决于 key 的具体情况，如复杂度（复杂对象）、离散度、冲突率等，换言之，取决于 HashMap 查找 value 的开销；entrySet 一次性取出所有 key 和 value 的操作是有性能开销的，当这个损失小于 HashMap 查找 value 的开销时，entrySet 的性能优势就会体现出来；例如上述对比测试中，当 key 是最简单的数值字符串时，keySet 可能反而会更高效，耗时比 entrySet 少10%，总体来说还是推荐使用 entrySet；因为当 key 很简单时，其性能或许会略低于 keySet，但却是可控的；而随着 key 的复杂化，entrySet 的优势将会明显体现出来；
- 遍历只需要 key 时：keySet 方法更为合适，因为 entrySet 将无用的 value 也给取出来，浪费性能和空间；
- 只遍历 value 时，使用vlaues方法是最佳选择；

如果你是遍历 TreeMap：

- 同时遍历 key 和 value 时，与 HashMap 不同，entrySet 的性能远远高于 keySet，这是由 TreeMap 的查询效率决定的，也就是说，TreeMap 查找 value 的开销较大，明显高于 entrySet 一次性取出所有 key 和 value 的开销；因此，遍历 TreeMap 时强烈推荐使用 entrySet 方法；

## HashMap

HashMap 是基于数组+链表+红黑树组成的，数组是 HashMap 的基本结构，链表和红黑树则是主要为了解决哈希冲突、提高查找性能等方面，如果定位到的数组位置不含链表（当前 node 的 next 指向 null），那么对于查找，添加等操作很快，仅需一次寻址即可；如果定位到的数组包含链表，对于添加操作，其时间复杂度为 $O(n)$，首先遍历链表，存在即覆盖，否则新增；对于查找操作来讲，仍需遍历链表，然后通过 key 对象的 equals 方法逐一比对查找。所以，性能考虑，HashMap 中的链表出现越少，性能才会越好。

HashMap 中几个重要的参数

```java
//实际存储的key-value键值对的个数
transient int size;
//阈值，当table == {}时，该值为初始容量（初始容量默认为16）；当table被填充了，也就是为table分配内存空间后，threshold一般为 capacity*loadFactory。HashMap在进行扩容时需要参考threshold，后面会详细谈到
int threshold;
//负载因子，代表了table的填充度有多少，默认是0.75
final float loadFactor;
//用于快速失败，由于HashMap非线程安全，在对HashMap进行迭代时，如果期间其他线程的参与导致HashMap的结构发生变化了（比如put，remove等操作），需要抛出异常ConcurrentModificationException
transient int modCount;
```

HashMap 在 1.7 和 1.8 之间的变化：

- 1.7 采用数组+单链表，1.8 在单链表超过 8，且数组长度超过 64 后才采用红黑树存储，当数组容量小于 64 时，只会进行扩容;
- 1.7 扩容时需要重新计算哈希值和索引位置，1.8 并不重新计算哈希值，巧妙地采用和扩容后容量进行 & 操作来计算新的索引位置，如果 key 的 hash 值与旧容量之间 & 运算为 0（e.hash & oldCap == 0），则存放在原来的位置；如果 （e.hash & oldCap != 0，因为扩容后，最高位相同，所以不为 1）则存放的位置为 index + oldCap；
- 1.7 插入元素到单链表中采用头插入法，1.8 采用的是尾插入法；

## HashTable

基本和 HashMap 一样，只是它所有的方法都是被 synchronized 修饰的，包括 toString() 方法，所以效率是很低的，基本不会再使用它了。

## IdentityHashMap

IdentityHashMap 是基于一个纯数组实现的，默认容量为32，且有一个最小容量 4，允许的最大容量为 $2^{29}$，默认的负载因子为 2/3，在创建 IdentityHashMap 时，如果指定的初始容量为 initCpacity，那么创建的 Object[] 数组的长度为 2 * initCapacity；

允许存在 null key 和 null value，IdentityHashMap 将 null key 包装成一个 Object 对象，也就是所有 null key 都会插入到同一个位置，后插入 null key 的 value 会替换前面插入 null key 对应的值；

只有当且仅当两个 key 通过 “==” 判断相等且哈希值相等，IdentityHashMap 才认为这两个key 相等；

key 存放在偶数索引上，value 存放在奇数索引上；

IdentityHashMap 允许的最大容量为 $2^{29}$，而 HashMap 的容量为 $2^{30}$，这是因为 IdentityHashMap 使用数组来存储 key、value，如果最大容量设置为 $2^{30}$，那么底层创建的 Object 数组的长度为 $2^{31}$，将超过 Integer 允许的最大值，因此，将 IdentityHashMap 的最大容量设置为 $2^{29}$；

## TreeMap

TreeMap 具有如下特性：

- 即基于红黑树算法的实现，本质上一棵红黑树；
- TreeMap 比 HashMap 要慢一些，因为 HashMap 前面还做了一层桶，寻找元素要快很多；
- 因为 TreeMap 是基于红黑树实现的，因此，在 TreeMap 中没有扩容的概念；
- TreeMap 的遍历不是采用传统的递归式遍历；
- TreeMap 可以按范围查找元素，查找最近的元素，这也是 TreeMap 相对于 HashMap 的一大特点；

## LinkedHashMap

LinkedHashMap 是一种有序的哈希表，通过维持一个双向链表来维持元素的顺序，它是 HashMap 的子类，因此更准确地说，它是将 HashMap 中的所有 Node 节点链入一个双向链表，由于 LinkedHashMap 是 HashMap 的子类，所以 LinkedHashMap 自然会拥有 HashMap 的所有特性。比如，LinkedHashMap 的元素存取过程基本与HashMap 基本类似，只是在细节实现上稍有不同。当然，这是由 LinkedHashMap 本身的特性所决定的，因为它额外维护了一个双向链表用于保持迭代顺序。此外，LinkedHashMap 可以很好的支持 LRU算法。

虽然 LinkedHashMap 增加了时间和空间上的开销，但是它通过维护一个**额外的双向链表保证了迭代顺序**。特别地，该迭代顺序可以是插入顺序，也可以是访问顺序。

## WeakHashMap

WeakHashMap 的存储结构只有（数组 + 链表），是以弱键实现哈希表，内部的 key 会存储为弱引用，当 JVM gc 时，如果这些 key 没有强引用存在的话，会被 gc 回收掉，下一次当我们操作 WeakHashMap  时会把对应的 Entry 整个删除掉，基于这种特性，WeakHashMap 特别适用于缓存处理。

gc 时会被清除，会清除没有强引用的 key，每次对 WeakHashMap 的操作都会剔除失效 key 对应的 Entry。

使用 String 作为 key 时，一定要使用 new String() 这样的方式声明 key，才会失效，其它的基本类型的包装类型是一样的。

## ConcurrentHashMap

在 jdk1.8 前 ConcurrentHashMap 使用分段锁，Segment 其实就是一个 HashMap，Segment 也包含一个 HashEntry 数组，数组中的每一个 HashEntry 既是一个键值对，也是一个链表的头节点。

Segment 对象在 ConcurrentHashMap 集合中有 $2^n$ 方个，共同保存在一个名为 **segments 的数组当中**，因此ConcurrentHashMap 的结构为：

![ConcurrentHashMap-jdk1.7结构.png](http://ww1.sinaimg.cn/large/70dc5b5cgy1g77lya559sj20qc0em0tx.jpg)

ConcurrentHashMap 是一个双层哈希表，在一个总的哈希表下面，有若干个子哈希表。（这样的双层结构，类似于数据库水平拆分来理解）ConcurrentHashMap 如此的设计，优势主要在于： 每个 segment 的读写是高度自治的，segment 之间互不影响，这称之为**“锁分段技术”**；

- 不同的 Segment 是可以并发执行 put 操作的

- 同一 segment 的写入是上锁的，对同一 segment 的并发写入会被阻塞；
- 同一 segment 的一写一读

在 jdk1.8 中，为了提高哈希碰撞下的寻址性能，采用数组 + 单链表 + 红黑树的方式，当链表长度超过一定阈值（某个哈希桶中单链表长度等于 8，且此时哈希桶的长度大于等于 64）时将链表（寻址时间复杂度为 $O(n)$ ）转换为红黑树（寻址时间复杂度为 $O(long(n)$ ）。

![](http://ww1.sinaimg.cn/large/70dc5b5cgy1g667g9bgiej20q80bbjso.jpg)

相对 jdk1.7 中采用分段锁的方式，jdk1.8 中 ConcurrentHashMap 在设计上有如下几点变化:

1. 不采用 Segment 而采用 Node，锁住 Node 来实现减小锁粒度；
2. 设计了 MOVED 状态，当 resize 的中过程中，当某个线程在扩容时，此时另外一个线程进行 put 操作，那么这个进行 put 操作的线程会帮助扩容的线程进行扩容；
3. 使用 3 个 CAS 操作来确保 Node 的一些操作的原子性，这种方式代替锁；
4. sizeCtl 不同值来代表不同含义，起到了控制的作用；
5. 采用 synchronized、CAS方式来实现并发，而不是 ReentrantLock；

## ConcurrentSkipListMap

ConcurrentSkipListMap 是基于跳跃表实现的哈希表；ConcurrentSkipListMap 基于跳表实现的哈希表，跳表在有序的单链表增加多级索引，通过索引来实现快速查找的一种数据结构；不允许 key value、为 null，如果插入一个 key 或者 value 为 null 的键值对，则抛出 NullPointerException 异常；

在 ConcurrentSkipListMap中存在三种节点：

- Node，数据节点，存储数据的节点，典型的单链表结构；
- Index，索引节点，存储着对应的 node 值，及向下和向右的索引指针；
- HeadIndex，头索引节点，继承自 Index，value 值为固定的 BASE_HEADER 值，并扩展一个 level 字段，用于记录索引的层级；

put、remove、get、containsKey 方法的时间复杂度为 $O(log(n))$；

## HashMap 为什么线程不安全？有什么影响？

**线程不安全的原因**

由于 HashMap 的 put、resize 方法都不是线程安全的方法，在扩容过程中，会新生成一个新容量的数组，然后对原数组的所有键值对重新进行计算和写入新的数组，之后指向新生成的数组；如果多个线程检测到 HashMap 需要进行扩容，那么多个线程进入到 resize 方法中，那么多个线程都会各自生成新的数组并 rehash 后赋给该 HashMap 底层的数组 table，结果最终只有最后一个线程生成的新数组被赋给 table 变量，其他线程的数据均会丢失；

**在多线程情形下，HashMap 可能出现如下的问题**

- HashMap 在多线程 put 后可能导致 get 无限循环，出现死锁的问题，导致 CPU 的使用率为 100%（jdk1.7，jdk1.8？）；
- 多线程 put 的时候可能导致元素丢失；

**原理**

HashMap 的底层存储结构是一个Node 数组，一旦发生哈希冲突时，HashMap 采用拉链法解决碰撞冲突，Node 内部的变量：

```java
final int hash;
final K key;
V value;
Node<K,V> next;
```

通过Node 内部的 next 变量可以知道使用的是链表，如果在某一时刻多个线程同时执行 put 操作，而有大于两个 key 的 hash 值相同，如图中 a1、a2，这个时候需要解决碰撞冲突，解决 hash 冲突就是将 key 不同而相同 hash 值相同的两个 key-value 链接到单链表中，暂且不讨论是从链表头部插入还是从尾部初入，这个时候两个线程如果恰好都取到了对应位置的头结点 e1，而最终的结果可想而知，a1、a2 两个数据中势必会有一个会丢失

![HashMap-多线程put丢失值.png](http://ww1.sinaimg.cn/large/70dc5b5cgy1g77lkxd5fyj20kf0900u4.jpg)

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

put 方法不是同步的，因此，多个线程同时调用了 putVal 方法也不是同步的，所以导致了线程不安全出现问题。

HashMap 在添加 key-value 时，如果容量不够，要进行扩容，扩容方法 resize 方法也不是一个同步的方法。

在扩容过程中，会新生成一个新容量的数组，然后对原数组的所有键值对重新进行计算和写入新的数组，之后指向新生成的数组。

当多个线程同时检测到 HashMap 的键值对数超过阈值的时候就会同时调用 resize 方法进行扩容，各自生成新的数组并 rehash 后赋给该 HashMap 底层的数组 table，结果最终只有最后一个线程生成的新数组被赋给 table 变量，其他线程的均会丢失。而且当某些线程已经完成赋值而其他线程刚开始的时候，就会用已经被赋值的 table 作为原始数组，这样也会有问题的。

## 使用线程安全的 Map

- HashTable 或者 Collections.synchronizedMap，原理都是使用 synchronized 关键字来保证线程安全，但这两者不管是读还是写操作，它们都会给整个集合上锁，导致同一时间的其他操作被阻塞，虽然解决了 HashMap 的线程不安全的问题，但是带来了运行效率不佳的问题；
- ConcurrentHashMap、ConcurrentSkipListMap 都是采用的 CAS 操作来实现线程安全；

## 为什么 ConcurrentHashMap#get 方法没有加锁

其实这道题就是考察对 volatile 关键字的理解。

>Java 中 volatile 关键字来保证可见性、有序性，禁止进行指令重排序，但 volatile 不保证原子性。
>
>普通的共享变量不能保证可见性，因为普通共享变量被修改之后，什么时候被写入主存是不确定的，当其他线程去读取时，此时内存中可能还是原来的旧值，因此无法保证可见性。
>
>当共享变量使用 volatile 修饰后会强制当前线程将修改的值立即写入主存，因此，当一个线程修改 volatile 变量后，其他的线程是立即可以看到当前线程对变量的修改（其他线程会将自己的本地内存中变量值置于无效，重新从主内存中获取变量的值）。
>
>volatile 关键字对于基本类型的修改可以在随后对多个线程的读保持一致，但是对于引用类型如数组，实体 bean，仅仅保证引用的可见性，但并不保证引用内容的可见性。

```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;
    final K key;
    volatile V val;
    volatile Node<K,V> next;

    Node(int hash, K key, V val, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.val = val;
        this.next = next;
    }
    
    ......
        
}
```

ConcurrentHashMap#Node 类的 val、next 属性使用 volatile 关键字修饰，如上所示。

由于 Node 的 val 属性和 next 指针是用 volatile 修饰的，因此，在多线程环境下，某哥线程修改结点的 val 或者新增节点的是对其他线程可见的，

<font color=red>ConcurrentHashMap#put 方法采用的是 CAS 操作和 synchronized 关键字保证线程安全，而不是使用加锁的方式。</font>

```java
transient volatile Node<K,V>[] table;
```

table 使用 volatile 关键字就是为了使得 table 在扩容的时候对其他线程具有可见性。

## ConcurrentHashMap 出现死循环

```java
ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
// map默认capacity 16，当元素个数达到(capacity - capacity >> 2) = 12个时会触发rehash
for (int i = 0; i < 11; i++) {
    map.put(i, i);
}

map.computeIfAbsent(12, (k) -> {
    // 这里会导致死循环
    map.put(100, 100);
    return k;
});
```

**原因如下：**

当执行 computeIfAbsent 方法时，如果 key 对应的 slot 为空，此时会创建 ReservationNode 对象（hash 值为 RESERVED = -3）放到当前 slot 位置，然后调用 mappingFunction.apply(key) 方法生成 value，根据 value 创建Node 之后赋值到 slow 位置，此时完成 computeIfAbsent 流程。

但是上述代码 mappingFunction 中又对该 ConcurrentHashMap 进行了一次 put 操作，并且触发了 rehash 操作，在 transfer 方法中遍历 slot 数组时，依次判断 slot 对应 Node 是否为 null、hash 值是否为 MOVED = -1、hash 值否大于 0（链表结构)、Node 类型是否是 TreeBin（红黑树节点），唯独没有判断 hash 值为 RESERVED=-3 的情况，因此导致了死循环问题。

**解决办法：**

不要在 mappingFunction 中再对当前 map 进行更新操作，尽量减少使用 computeIfAbsent 方法。