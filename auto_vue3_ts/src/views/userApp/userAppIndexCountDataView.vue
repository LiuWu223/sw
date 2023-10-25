<template>
  <div class="flex justify-space-between mb-4 flex-wrap gap-4" style="width: 100%;">
    <div style="margin-top: 20px">
    <el-radio-group v-model="dataDate" size="small">
      <el-radio-button label="年" />
      <el-radio-button label="月" />
      <el-radio-button label="日" />
    </el-radio-group>
  </div>
  </div>
  <el-row
    style="
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 100%;
    margin-bottom: 32px;"
    :gutter="16">
    <el-col :span="8">
      <div class="statistic-card">
        <el-statistic :value="98500">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              下发测试任务数量(个)
              <el-tooltip
                effect="dark"
                content="统计所有下发测试任务数量，按每月增长计算涨幅"
                placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>比上{{ dataDate }}增长</span>
            <span class="green">
              24%
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="statistic-card">
        <el-statistic :value="693700">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              测试失败任务(个)
              <el-tooltip
                effect="dark"
                content="测试失败任务数量统计"
                placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>与上{{ dataDate }}增长</span>
            <span class="red">
              12%
              <el-icon>
                <CaretBottom />
              </el-icon>
            </span>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="statistic-card">
        <el-statistic :value="3" title="New transactions today">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              测试使用时长(时)
              <el-tooltip
                effect="dark"
                content="统计共使用时长"
                placement="top"
              >
                <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                </el-icon>
              </el-tooltip>
            </div>
          </template>
        </el-statistic>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>与上{{ dataDate }}增长</span>
            <span class="green">
              16%
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import {
  CaretBottom,
  CaretTop,
  Warning
} from '@element-plus/icons-vue'
import { ref } from 'vue'

const dataDate = ref('年')
</script>

<style scoped>
:global(h2#card-usage ~ .example .example-showcase) {
  background-color: var(--el-fill-color) !important;
}

.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  height: 100%;
  padding: 20px;
  border-radius: 4px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
  width: 180px;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}
.red {
  color: var(--el-color-error);
}
</style>
