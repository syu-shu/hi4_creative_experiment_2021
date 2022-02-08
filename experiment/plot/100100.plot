set terminal "png"
set output "100100.png"
set logscale x
set nokey
# set xrange [0:100]

plot 4425,4425*9/10,"../results/100/100/1642640148719" every 30 with lp pt 0, "../results/100/100/1642640179884" every 30 with lp pt 0, "../results/100/100/1642640291106" every 30 with lp pt 0, "../results/100/100/1642640332715" every 30 with lp pt 0, "../results/100/100/1642640385508" every 30 with lp pt 0