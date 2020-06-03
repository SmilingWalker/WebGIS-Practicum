let a = 'aaaa'
let b = 'bbb'

let flag = true
//commonJS 导出
// module.exports = {
//     a,
//     b
// }
function sum(sum1,sum2) {
    return sum1+sum2
}
//导出方式  1
export {
    flag,sum
}

//导出方式二
export let kk = "aaa";

// 只能有一个，默认导出
